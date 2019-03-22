package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("aTeamService")
public class ATeamService {

    private final InsightOrganisationUnitService organisationUnitService;
    private InsightEmployeeService employeeService;

    Map<ATeamPair, Double> allScoresCached = new HashMap<>();

    @Autowired
    public ATeamService(InsightOrganisationUnitService organisationUnitService, InsightEmployeeService employeeService) {
        this.organisationUnitService = organisationUnitService;
        this.employeeService = employeeService;
    }

    public Double getScores(ATeamPair pair) {
        return this.allScoresCached.get(pair);
    }


    public List<ATeam> calculateATeams(int nrOfTeamMembers, List<Location> locations) {
        Map<ATeamPair, Double> allScores = new HashMap<>();

        List<OrganisationUnit> focusGroups = organisationUnitService.getFocusGroups();

        Map<ATeamPair, Double> aTeamsByFocusGroup = calculateScore(focusGroups, locations);

        allScores.putAll(aTeamsByFocusGroup);

        List<OrganisationUnit> zuehlkeTeams = organisationUnitService.getTeams();

        Map<ATeamPair, Double> aTeamsByZuehlkeTeam = calculateScore(zuehlkeTeams, locations);

        allScores = combineValues(allScores, aTeamsByZuehlkeTeam);

        cacheScores(allScores);

        ArrayList<ATeam> aTeams = new ArrayList<>();
        while (!allScores.isEmpty() && aTeams.size() <= 3) {
            ATeam aTeam = calculateTeam(nrOfTeamMembers, allScores);
            aTeams.add(aTeam);
        }

        int count = 0;
        for (ATeam aTeam : aTeams) {
            System.out.println("Team: " + count++);
            for(int i = 0; i<nrOfTeamMembers -1; i++) {
                for(int j= i+1; j<nrOfTeamMembers; j++) {
                    Employee employee1 = aTeam.getTeamMembers().get(i).getEmployee();
                    Employee employee2 = aTeam.getTeamMembers().get(j).getEmployee();
                    double workedWithScore = employeeService.getWorkedWith(employee1.getCode(), employee2.getCode());
                    aTeam.setScore(new Score(aTeam.getScore().getValue() + workedWithScore));
                }
            }
            System.out.println("done!");
        }

        Collections.sort(aTeams);
        return aTeams;
    }

    private void cacheScores(Map<ATeamPair, Double> allScores) {
        allScoresCached.clear();
        allScoresCached.putAll(allScores);
    }

    ATeam calculateTeam(int nrOfTeamMembers, Map<ATeamPair, Double> allScores) {
        ATeamPair pairWithHighestScore = findPairWithHighestScore(allScores);
        if (pairWithHighestScore == null) {
            return new ATeam();
        }
        allScores.remove(pairWithHighestScore);

        List<ATeamMember> allTeamMembers = new ArrayList<>();
        allTeamMembers.add(new ATeamMember(pairWithHighestScore.getE1()));
        allTeamMembers.add(new ATeamMember(pairWithHighestScore.getE2()));
        int newNumberOfTeamMembers = nrOfTeamMembers - 2;

        int firstEmployeeNumberOfFriends = newNumberOfTeamMembers / 2;
        int secondEmployeeNumberOfFriends = newNumberOfTeamMembers - firstEmployeeNumberOfFriends;

        List<ATeamMember> allTeamMembersOfFirstEmployee = findNextPairs(allScores, firstEmployeeNumberOfFriends, pairWithHighestScore.getE1(), allTeamMembers);
        allTeamMembers.addAll(allTeamMembersOfFirstEmployee);

        List<ATeamMember> allTeamMembersOfSecondEmployee = findNextPairs(allScores, secondEmployeeNumberOfFriends, pairWithHighestScore.getE2(), allTeamMembers);
        allTeamMembers.addAll(allTeamMembersOfSecondEmployee);

        return new ATeam(allTeamMembers, new Score(1.0));
    }

    List<ATeamMember> findNextPairs(Map<ATeamPair, Double> allScores, int numberOfFriends, Employee employee, List<ATeamMember> allTeamMembers) {
        List<ATeamMember> teamMembers = new ArrayList<>();
        ATeamPair nextPair;
        for (int i = 0; i < numberOfFriends; ++i) {
            if (allScores.isEmpty()) {
                break;
            }

            ATeamMember nextMember = null;
            do {
                nextPair = getNextBestPair(allScores, employee);
                if (nextPair == null) {
                    break;
                }

                nextMember = getNextMemberNot(nextPair, employee);
                allScores.remove(nextPair);
            } while (allTeamMembers.contains(nextMember));

            if (!teamMembers.contains(nextMember)) {
                teamMembers.add(nextMember);
                allScores.remove(nextPair);
            }

        }

        return teamMembers;
    }

    private ATeamPair getNextBestPair(Map<ATeamPair, Double> allScores, Employee employee) {
        TreeMap<Double, List<ATeamPair>> nextPairs = new TreeMap<>(Comparator.reverseOrder());
        for (Map.Entry<ATeamPair, Double> aTeamPair : allScores.entrySet()) {
            ATeamPair nextPair = aTeamPair.getKey();
            if (employee.equals(nextPair.getE1()) || employee.equals(nextPair.getE2())) {
                Double score = aTeamPair.getValue();
                if (nextPairs.containsKey(score)) {
                    nextPairs.get(score).add(nextPair);
                } else {
                    ArrayList<ATeamPair> pairs = new ArrayList<>();
                    pairs.add(nextPair);
                    nextPairs.put(score, pairs);
                }
            }
        }
        Map.Entry<Double, List<ATeamPair>> bestPairs = nextPairs.firstEntry();

        if (bestPairs == null || bestPairs.getValue() == null || bestPairs.getValue().get(0) == null) {
            return null;
        }

        return bestPairs.getValue().get(0);

    }

    private ATeamMember getNextMemberNot(ATeamPair nextPairOfOne, Employee employee) {
        if (nextPairOfOne.getE1().equals(employee)) {
            return new ATeamMember(nextPairOfOne.getE2());
        } else {
            return new ATeamMember(nextPairOfOne.getE1());
        }
    }

    private ATeamPair findPairWithHighestScore(Map<ATeamPair, Double> allScores) {
        TreeMap<Double, List<ATeamPair>> scoreMap = new TreeMap<>(Comparator.reverseOrder());
        for (Map.Entry<ATeamPair, Double> teamToScore : allScores.entrySet()) {
            if (scoreMap.containsKey(teamToScore.getValue())) {
                scoreMap.get(teamToScore.getValue()).add(teamToScore.getKey());
            } else {
                List<ATeamPair> teamPairs = new ArrayList<>();
                teamPairs.add(teamToScore.getKey());
                scoreMap.put(teamToScore.getValue(), teamPairs);
            }
        }

        Map.Entry<Double, List<ATeamPair>> firstEntry = scoreMap.firstEntry();
        if (firstEntry == null || firstEntry.getValue() == null || firstEntry.getValue().get(0) == null) {
            return null;
        }
        return firstEntry.getValue().get(0);

    }

    Map<ATeamPair, Double> combineValues(Map<ATeamPair, Double> allScores, Map<ATeamPair, Double> aTeamsByZuehlkeTeam) {
        Map<ATeamPair, Double> combinedScores = new HashMap<>(allScores);
        for (Map.Entry<ATeamPair, Double> aTeamPair : aTeamsByZuehlkeTeam.entrySet()) {
            ATeamPair key = aTeamPair.getKey();
            ATeamPair key2 = new ATeamPair(key.getE2(), key.getE1());
            if (allScores.containsKey(key) || allScores.containsKey(key2)) {
                Double val1 = allScores.get(key);
                Double val2 = allScores.get(key2);
                combinedScores.put(val1 == null ? key2 : key, aTeamPair.getValue() + (val1 == null ? val2 : val1));
            } else {
                combinedScores.put(key, aTeamPair.getValue());
            }
        }
        return combinedScores;
    }

    Map<ATeamPair, Double> calculateScore(List<OrganisationUnit> organisationUnits, List<Location> locations) {
        Map<ATeamPair, Double> scorePairs = new HashMap<>();

        for (OrganisationUnit organisationUnit : organisationUnits) {
            List<Employee> employees = organisationUnitService.getParticipantsInOrganisationUnit(organisationUnit.getId(), locations);
            for (Employee e1 : employees) {
                for (Employee e2 : employees) {
                    if (!e1.equals(e2)) {
                        ATeamPair pair = new ATeamPair(e1, e2);
                        ATeamPair pair2 = new ATeamPair(e2, e1);
                        if (scorePairs.containsKey(pair) || scorePairs.containsKey(pair2)) {
                            Double val1 = scorePairs.get(pair);
                            Double val2 = scorePairs.get(pair2);
                            scorePairs.put(val1 == null ? pair2 : pair, (val1 == null ? val2 : val1) + 1.0);
                        } else {
                            scorePairs.put(pair, 1.0);
                        }
                    }
                }
            }
        }
        return scorePairs;
    }
}

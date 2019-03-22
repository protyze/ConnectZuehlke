package ch.zuehlke.fullstack.ConnectZuehlke.apis.insight.service;

import ch.zuehlke.fullstack.ConnectZuehlke.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("aTeamService")
public class ATeamService {

    private final InsightOrganisationUnitService organisationUnitService;

    @Autowired
    public ATeamService(InsightOrganisationUnitService organisationUnitService) {
        this.organisationUnitService = organisationUnitService;
    }


    public List<ATeam> calculateATeams(int nrOfTeamMembers) {
        Map<ATeamPair, Double> allScores = new HashMap<>();

        List<OrganisationUnit> focusGroups = organisationUnitService.getFocusGroups();

        Map<ATeamPair, Double> aTeamsByFocusGroup = calculateScore(focusGroups);

        allScores.putAll(aTeamsByFocusGroup);

        List<OrganisationUnit> zuehlkeTeams = organisationUnitService.getTeams();

        Map<ATeamPair, Double> aTeamsByZuehlkeTeam = calculateScore(zuehlkeTeams);

        allScores = combineValues(allScores, aTeamsByZuehlkeTeam);

        ArrayList<ATeam> aTeams = new ArrayList<>();
        while (!allScores.isEmpty() && aTeams.size() <= 3) {
            ATeam aTeam = calculateTeam(nrOfTeamMembers, allScores);
            aTeams.add(aTeam);
        }

        return aTeams;
    }

    ATeam calculateTeam(int nrOfTeamMembers, Map<ATeamPair, Double> allScores) {
        ATeamPair pairWithHighestScore = findPairWithHighestScore(allScores);
        allScores.remove(pairWithHighestScore);

        List<ATeamMember> aTeamMembers = new ArrayList<>();
        aTeamMembers.add(new ATeamMember(pairWithHighestScore.getE1()));
        aTeamMembers.add(new ATeamMember(pairWithHighestScore.getE2()));
        int newNumberOfTeamMembers = nrOfTeamMembers - 2;

        int firstEmployeeNumberOfFriends = newNumberOfTeamMembers / 2;
        int secondEmployeeNumberOfFriends = newNumberOfTeamMembers - firstEmployeeNumberOfFriends;

        List<ATeamMember> allTeamMembersOfFirstEmployee = findNextPairs(allScores, firstEmployeeNumberOfFriends, pairWithHighestScore.getE1());
        aTeamMembers.addAll(allTeamMembersOfFirstEmployee);

        List<ATeamMember> allTeamMembersOfSecondEmployee = findNextPairs(allScores, secondEmployeeNumberOfFriends, pairWithHighestScore.getE2());
        aTeamMembers.addAll(allTeamMembersOfSecondEmployee);

        return new ATeam(aTeamMembers, new Score(1.0));
    }

    List<ATeamMember> findNextPairs(Map<ATeamPair, Double> allScores, int numberOfFriends, Employee employee) {

        List<ATeamMember> teamMembers = new ArrayList<>();
        ATeamPair nextPair;
        for (int i = 0; i < numberOfFriends; ++i) {
            if (allScores.isEmpty()) {
                break;
            }
            nextPair = getNextBestPair(allScores, employee);
            if (nextPair == null) {
                break;
            }
            ATeamMember nextMember = getNextMemberNot(nextPair, employee);

            if (!teamMembers.contains(nextMember)) {
                teamMembers.add(nextMember);
                allScores.remove(nextPair);
            }
        }

        return teamMembers;
    }

    private ATeamPair getNextBestPair(Map<ATeamPair, Double> allScores, Employee employee) {
        TreeMap<Double, ATeamPair> nextPairs = new TreeMap<>(Comparator.reverseOrder());
        for (Map.Entry<ATeamPair, Double> aTeamPair : allScores.entrySet()) {
            ATeamPair nextPair = aTeamPair.getKey();
            if (employee.equals(nextPair.getE1()) || employee.equals(nextPair.getE2())) {
                Double score = aTeamPair.getValue();
                nextPairs.put(score, nextPair);
            }
        }
        Map.Entry<Double, ATeamPair> doubleATeamPairEntry = nextPairs.firstEntry();
        if (doubleATeamPairEntry == null) {
            return null;
        }
        return doubleATeamPairEntry.getValue();

    }

    private ATeamMember getNextMemberNot(ATeamPair nextPairOfOne, Employee employee) {
        if (nextPairOfOne.getE1().equals(employee)) {
            return new ATeamMember(nextPairOfOne.getE2());
        } else {
            return new ATeamMember(nextPairOfOne.getE1());
        }
    }

    private ATeamPair findPairWithHighestScore(Map<ATeamPair, Double> allScores) {
        TreeMap<Double, ATeamPair> scoreMap = new TreeMap<>(Comparator.reverseOrder());
        for (Map.Entry<ATeamPair, Double> teamToScore : allScores.entrySet()) {
            scoreMap.put(teamToScore.getValue(), teamToScore.getKey());
        }

        return scoreMap.firstEntry().getValue();

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

    Map<ATeamPair, Double> calculateScore(List<OrganisationUnit> organisationUnits) {
        Map<ATeamPair, Double> scorePairs = new HashMap<>();

        for (OrganisationUnit organisationUnit : organisationUnits) {
            List<Employee> employees = organisationUnitService.getParticipantsInOrganisationUnit(organisationUnit.getId());
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

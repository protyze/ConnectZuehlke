import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import * as go from "gojs";

@Component({
  selector: 'relation-wheel',
  templateUrl: './relation-wheel.component.html',
  styleUrls: ['./relation-wheel.component.scss']
})
export class RelationWheelComponent implements OnInit {

  @ViewChild('myDiagramDiv')
  element: ElementRef;

  constructor() { }

  ngOnInit() {
  }

  ngAfterViewInit() {
    this.renderGraph();
  }

  renderGraph(): void {
    function WheelLayout() {
      go.CircularLayout.call(this);
    }
    go.Diagram.inherit(WheelLayout, go.CircularLayout);

    // override makeNetwork to set the diameter of each node and ignore the TextBlock label
    WheelLayout.prototype.makeNetwork = function(coll) {
      var net = go.CircularLayout.prototype.makeNetwork.call(this, coll);
      net.vertexes.each(function(cv) {
        cv.diameter = 20;  // because our desiredSize for nodes is (20, 20)
      });
      return net;
    }

    // override commitNodes to rotate nodes so the text goes away from the center,
    // and flip text if it would be upside-down
    WheelLayout.prototype.commitNodes = function() {
      go.CircularLayout.prototype['commitNodes'].call(this);
      this.network.vertexes.each(function(v) {
        var node = v.node;
        if (node === null) return;
        // get the angle of the node towards the center, and rotate it accordingly
        var a = v.actualAngle;
        if (a > 90 && a < 270) {  // make sure the text isn't upside down
          var textBlock = node.findObject("TEXTBLOCK");
          textBlock.angle = 180;
        }
        node.angle = a;
      });
    };

    // override commitLinks in order to make sure all of the Bezier links are "inside" the ellipse;
    // this helps avoid links crossing over any other nodes
    WheelLayout.prototype.commitLinks = function() {
      go.CircularLayout.prototype['commitLinks'].call(this);
      if (this.network.vertexes.count > 4) {
        this.network.vertexes.each(function(v) {
          v.destinationEdges.each(function(de) {
            var dv = de.toVertex;
            var da = dv.actualAngle;
            var sa = v.actualAngle;
            if (da - sa > 180) da -= 360;
            else if (sa - da > 180) sa -= 360;
            de.link.curviness = (sa > da) ? 15 : -15;
          })
        })
      }
    }
    // end WheelLayout class


    var highlightColor = "red";  // color parameterization
    var myDiagram;

    function init(element: ElementRef) {
      // if (window.goSamples) {
      // goSamples();
      // }init for these samples -- you don't need to call this
      let $: any = go.GraphObject.make;  // for conciseness in defining templates

      myDiagram = $(go.Diagram, element /*document.querySelector('#myDiagramDiv')*/ , // must be the ID or reference to div
        {
          initialAutoScale: go.Diagram.Uniform,
          padding: 10,
          contentAlignment: go.Spot.Center,
          layout:
            $(WheelLayout,  // set up a custom CircularLayout
              // set some properties appropriate for this sample
              {
                arrangement: go.CircularLayout.ConstantDistance,
                nodeDiameterFormula: go.CircularLayout.Circular,
                spacing: 10,
                aspectRatio: 0.7,
                sorting: go.CircularLayout.Optimized
              }),
          isReadOnly: true,
          click: function(e) {  // background click clears any remaining highlighteds
            e.diagram.startTransaction("clear");
            e.diagram.clearHighlighteds();
            e.diagram.commitTransaction("clear");
          }
        });

      // define the Node template
      myDiagram.nodeTemplate =
        $(go.Node, "Horizontal",
          {
            selectionAdorned: false,
            locationSpot: go.Spot.Center,  // Node.location is the center of the Shape
            locationObjectName: "SHAPE",
            mouseEnter: function(e, node) {
              node.diagram.clearHighlighteds();
              node.linksConnected.each(function(l) { highlightLink(l, true); });
              node.isHighlighted = true;
              var tb = node.findObject("TEXTBLOCK");
              if (tb !== null) tb.stroke = highlightColor;
            },
            mouseLeave: function(e, node) {
              node.diagram.clearHighlighteds();
              var tb = node.findObject("TEXTBLOCK");
              if (tb !== null) tb.stroke = "black";
            }
          },
          new go.Binding("text", "text"),  // for sorting the nodes
          $(go.Shape, "Ellipse",
            {
              name: "SHAPE",
              fill: "lightgray",  // default value, but also data-bound
              stroke: "transparent",  // modified by highlighting
              strokeWidth: 2,
              desiredSize: new go.Size(20, 20),
              portId: ""
            },  // so links will go to the shape, not the whole node
            new go.Binding("fill", "color"),
            new go.Binding("stroke", "isHighlighted",
              function(h) { return h ? highlightColor : "transparent"; })
              .ofObject()),
          $(go.TextBlock,
            { name: "TEXTBLOCK" },  // for search
            new go.Binding("text", "text"))
        );

      function highlightLink(link, show) {
        link.isHighlighted = show;
        link.fromNode.isHighlighted = show;
        link.toNode.isHighlighted = show;
      }

      // define the Link template
      myDiagram.linkTemplate =
        $(go.Link,
          {
            routing: go.Link.Normal,
            curve: go.Link.Bezier,
            selectionAdorned: false,
            mouseEnter: function(e, link) { highlightLink(link, true); },
            mouseLeave: function(e, link) { highlightLink(link, false); }
          },
          $(go.Shape,
            new go.Binding("stroke", "isHighlighted",
              function(h, shape) { return h ? highlightColor : shape.part.data.color; })
              .ofObject(),
            new go.Binding("strokeWidth", "isHighlighted",
              function(h) { return h ? 2 : 1; })
              .ofObject())
          // no arrowhead -- assume directionality of relationship need not be shown
        );

      generateGraph();
    }

    function generateGraph() {
      var names = [
        "Joshua", "Daniel", "Robert", "Noah", "Anthony",
        "Elizabeth", "Addison", "Alexis", "Ella", "Samantha",
        "Joseph", "Scott", "James", "Ryan", "Benjamin",
        "Walter", "Gabriel", "Christian", "Nathan", "Simon",
        "Isabella", "Emma", "Olivia", "Sophia", "Ava",
        "Emily", "Madison", "Tina", "Elena", "Mia",
        "Jacob", "Ethan", "Michael", "Alexander", "William",
        "Natalie", "Grace", "Lily", "Alyssa", "Ashley",
        "Sarah", "Taylor", "Hannah", "Brianna", "Hailey",
        "Christopher", "Aiden", "Matthew", "David", "Andrew",
        "Kaylee", "Juliana", "Leah", "Anna", "Allison",
        "John", "Samuel", "Tyler", "Dylan", "Jonathan",
      ];

      var nodeDataArray = names.map( (name, index) => {
        return {
          key: index,
          text: name,
          color: go.Brush.randomColor(128, 240)
        }
      });


      var linkDataArray = [];
      var num = nodeDataArray.length;
      for (let i = 0; i < num * 2; i++) {
        let a = Math.floor(Math.random() * num);
        let b = Math.floor(Math.random() * num / 4) + 1;
        linkDataArray.push({ from: a, to: (a + b) % num, color: go.Brush.randomColor(0, 127) });
      }

      console.log('nodeDataArray', nodeDataArray);
      console.log('linkDataArray', linkDataArray);

      myDiagram.model = new go.GraphLinksModel(nodeDataArray, linkDataArray);
    }
    init(this.element.nativeElement);
  }

}

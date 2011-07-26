(function(window) {
            
    $(document).ready(function() {

        var toCluster = function(obj, cur) {
            var clust = cur.cluster;
            if(!obj[clust]) { obj[clust] = []; }

            obj[clust].push(cur);
            return obj;
        };
        
        var clusters = _.reduce(finData, toCluster, {});

        _.each(clusters, function(cur, i) {

            var balances = _.flatten(_.reduce(cur, function(list, user) {
                                var userBalances = _.map(user.balances, function(ub) { return ub.balance; })
                                list.push(userBalances);
                                return list;
                            }, []));

            var times = _.flatten(_.reduce(cur, function(list, user) {
                                var userTimes = _.map(user.balances, function(ub) { return ub.timestamp; })
                                list.push(userTimes);
                                return list;
                            }, []));

            var w = 600,
            h = 300,
            leftMargin = 50,
            topMargin = 10,
            bottomMargin = 25,
            rightMargin = 20,
            yscale = d3.scale.linear().domain([0, d3.max(balances)]).range([0 + bottomMargin, h - topMargin]),
            xscale = d3.scale.linear().domain([d3.min(times), d3.max(times)]).range([0 + leftMargin, w - rightMargin]);

            //do d3 awesomeness here
            var viz = d3.select("#vizContainer")
                        .append("svg:svg")
                        .attr("width", w)
                        .attr("height", h);

            var g = viz.append("svg:g")
                        .attr("transform", "translate(0, 300)");

            _.each(cur, function(userLine, i) {
                var line = d3.svg.line()
                            .x(function(d) {return xscale(d.timestamp)})
                            .y(function(d) {return -1 * yscale(d.balance)});

                g.append("svg:path").attr("d", line(userLine.balances))
                    .attr("class", "line" + i);
            });


            g.append("svg:line")
                .attr("class", "edge")
                .attr("x1", xscale(d3.min(times)))
                .attr("y1", -1 * yscale(0))
                .attr("x2", xscale(d3.max(times)))
                .attr("y2", -1 * yscale(0));
            
            g.append("svg:line")
                .attr("class", "edge")
                .attr("x1", xscale(d3.min(times)))
                .attr("y1", -1 * yscale(0))
                .attr("x2", xscale(d3.min(times)))
                .attr("y2", -1 * yscale(d3.max(balances)));

            g.selectAll(".xLabel")
                .data(xscale.ticks(5))
                .enter().append("svg:text")
                .attr("class", "xLabel")
                .text(function(d) {
                    var date = new Date(d);
                    var month = date.getMonth() + 1;
                    var day = date.getDate();
                    var year = date.getFullYear();
                    return month + "/" + day + "/" + year;
                })
                .attr("x", function(d) { return xscale(d) })
                .attr("y", 0)
                .attr("text-anchor", "start");

            g.selectAll(".yLabel")
                .data(yscale.ticks(6))
                .enter().append("svg:text")
                .attr("class", "yLabel")
                .text(String)
                .attr("x", leftMargin - 15)
                .attr("y", function(d) { return -1 * yscale(d) })
                .attr("text-anchor", "end")
                .attr("dy", 4);

        });


    });

})(window)

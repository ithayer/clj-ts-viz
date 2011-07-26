(function(window) {

    var fake = [
        {id: 0, balance: [
                            {time: 0, balance: 1000},
                            {time: 1, balance: 1050},
                            {time: 2, balance: 1400},
                            {time: 3, balance: 1220},
                            {time: 4, balance: 100}
                        ]},
        {id: 1, balance: [
                            {time: 0, balance: 100},
                            {time: 1, balance: 2000},
                            {time: 2, balance: 1400},
                            {time: 3, balance: 300},
                            {time: 4, balance: 800}
                        ]}

    ]
            
    $(document).ready(function() {

        $.each(fake, function(i, cur) {


            var times = $.map(cur.balance, function(val) { return val.time;}),
                balances = $.map(cur.balance, function (val) {return val.balance; });
            
            var w = 400,
                h = 200,
                margin = 20,
                yscale = d3.scale.linear().domain([0, d3.max(balances)]).range([0 + margin, h - margin]),
                xscale = d3.scale.linear().domain([d3.min(times), d3.max(times)]).range([0 + margin, w - margin]);

            var dots = [1,2,3];

            //do d3 awesomeness here
            var viz = d3.select("#vizContainer")
                        .append("svg:svg")
                        .attr("width", w)
                        .attr("height", h);

            var g = viz.append("svg:g")
                        .attr("transform", "translate(0, 200)");

            var line = d3.svg.line()
                                .x(function(d) {return xscale(d.time)})
                                .y(function(d) {return -1 * yscale(d.balance)});

            var test = line(cur.balance);

            g.append("svg:path").attr("d", line(cur.balance))
                                .attr("class", "line");
        });


    });

})(window)

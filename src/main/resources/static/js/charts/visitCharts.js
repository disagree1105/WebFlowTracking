function getVisitCountRealTime(appId){
        var visitCount=0;
        $.ajax({
                url: "http://localhost:8888/visitCountRealTime/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    visitCount=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询浏览实时数据");
             }
            })
        return visitCount;
}

function getVisitTrend(appId){
        var visitTrend=[];
        $.ajax({
                url: "http://localhost:8888/visitTrend/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    visitTrend=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询浏览趋势");
             }
            })
        return visitTrend;
    }    

Highcharts.setOptions({
    global: {
        useUTC: false
    }
});
function activeLastPointToolip(chart) {
    var points = chart.series[0].points;
    chart.tooltip.refresh(points[points.length -1]);
}
$('#visitTrendRealTime').highcharts({
    chart: {
        type: 'spline',
        animation: Highcharts.svg, // don't animate in old IE
        marginRight: 10,
        events: {
            load: function () {
                // set up the updating of the chart each second
                var series = this.series[0],
                    chart = this;
                setInterval(function () {
                    var x = (new Date()).getTime(), // current time
                        y = getVisitCountRealTime(GetQueryString('appId'));
                    series.addPoint([x, y], true, true);
                    activeLastPointToolip(chart)
                }, 1000 * 60);
            }
        }
    },
    title: {
        text: '访问用户数量趋势实时数据'
    },
    xAxis: {
        type: 'datetime',
        tickPixelInterval: 150
    },
    yAxis: {
        title: {
            text: '访问用户数量'
        },
        plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
        }]
    },
    tooltip: {
        formatter: function () {
            return '<b>' + this.series.name + '</b><br/>' +
                Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                Highcharts.numberFormat(this.y, 0);
        }
    },
    legend: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
    series: [{
        name: '访问用户数量',
        data: (function () {
            // generate an array of random data
            var data = [],
                time = (new Date()).getTime(),
                i;
            var count = new Array();
                count = getVisitTrend(GetQueryString('appId'));
            for (i = -19; i <= 0; i += 1) {
                data.push({
                    x: time + i * 1000 * 60 ,
                    y: count[i+19]
                });
            }
            return data;
        }())
    }],
    credits: {
        enabled: false
    }
}, function(c) {
    activeLastPointToolip(c)
});
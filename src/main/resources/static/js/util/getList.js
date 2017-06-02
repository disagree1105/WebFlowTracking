function getOperList(appId){
        var list=new Array();
        $.ajax({
                url: "http://localhost:8888/operList/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    list=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询操作列表");
             }
            })
        return list;
}

function getVisitList(appId){
        var list=new Array();
        $.ajax({
                url: "http://localhost:8888/visitList/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    list=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询访问列表");
             }
            })
        return list;
}

function getTrack(appId,userIp){
    var list=new Array();
    $.ajax({
                url: "http://localhost:8888/userTrack/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                        "userIp":userIp
                },
                success:function(data){
                    list=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询用户轨迹");
             }
            })
    return list;
}

function getErrorList(appId){
        var list=new Array();
        $.ajax({
                url: "http://localhost:8888/errorList/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    list=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询错误列表");
             }
            })
        return list;
}

function getErrorInfo(appId,errorMessage){
        var list=new Array();
        $.ajax({
                url: "http://localhost:8888/errorInfo/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                        "errorMessage":errorMessage
                },
                success:function(data){
                    list=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询错误详情");
             }
            })
        return list;
}
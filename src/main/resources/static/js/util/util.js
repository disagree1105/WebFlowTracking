	function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}
	function getAppName(){
	    var www=window.location.search;
	    console.log(www);
	    return decodeURI(www.substring(www.indexOf("appName=")+8,www.length));
    }

	function gotoHtml(url){
	if(GetQueryString('appId')==null){
		if(document.getElementById("errorTip"))
		{
			document.getElementById("errorTip").style.display="";
			document.getElementById("errorMessage").innerHTML="请先选择应用！";
		}
	}else{
		window.location.href=url+'?uid='+GetQueryString('uid')+'&appId='+GetQueryString('appId')+'&appName='+getAppName();
	}
	}

	function getUserName(uid){
		var username="";
		$.ajax({
                url: "http://localhost:8888/userInfo/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "uid":uid,
                },
                success:function(data){
                	var obj=eval(data);
                	if(obj.result.ret_code==1)
                    {
                    	username= obj.result._ret.username;
                    }
                },
             error:function(){
             	console.log("服务器连接失败，无法查询用户信息");
             }
            })
		return username;
	
	}


	function getToday4Data(appId){
		var result=new Array();
		$.ajax({
                url: "http://localhost:8888/today/appInfo/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                	for(var i=0;i<4;i++){
                	result[i]=data[i];
                	}
                },
             error:function(){
             	console.log("服务器连接失败，无法查询应用信息");
             }
            })
		return result;
	}

	function getApdex(appId){
		var apdexMap={};
		$.ajax({
                url: "http://localhost:8888/apdex/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    apdexMap=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询apdex");
             }
            })
        return apdexMap;
	}

    function getBrowserPercent(appId){
        var browserMap={};
        $.ajax({
                url: "http://localhost:8888/browser/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    browserMap=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询浏览器占比");
             }
            })
        return browserMap;
    }

    function getIspPercent(appId){
        var ispMap={};
        $.ajax({
                url: "http://localhost:8888/isp/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    ispMap=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询运营商占比");
             }
            })
        return ispMap;
    }

    function getOsPercent(appId){
        var osMap={};
        $.ajax({
                url: "http://localhost:8888/os/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    osMap=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询操作系统占比");
             }
            })
        return osMap;
    }

    function getOperBrowserPercent(appId){
        var map={};
        $.ajax({
                url: "http://localhost:8888/oper/browser/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    map=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询操作来源浏览器占比");
             }
            })
        return map;
    }

    function getOperIspPercent(appId){
        var map={};
        $.ajax({
                url: "http://localhost:8888/oper/isp/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    map=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询操作来源运营商占比");
             }
            })
        return map;
    }

    function getOperOsPercent(appId){
        var map={};
        $.ajax({
                url: "http://localhost:8888/oper/os/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    map=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询操作来源操作系统占比");
             }
            })
        return map;
    }

     function getErrorBrowserPercent(appId){
        var map={};
        $.ajax({
                url: "http://localhost:8888/error/browser/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    map=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询错误来源浏览器占比");
             }
            })
        return map;
    }

    function getErrorIspPercent(appId){
        var map={};
        $.ajax({
                url: "http://localhost:8888/error/isp/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    map=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询错误来源运营商占比");
             }
            })
        return map;
    }

    function getErrorOsPercent(appId){
        var map={};
        $.ajax({
                url: "http://localhost:8888/error/os/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    map=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询错误来源操作系统占比");
             }
            })
        return map;
    }

    function getAppUrl(appId){
        var url='';
        $.ajax({
                url: "http://localhost:8888/appUrl/get",
                type: "GET",
                dataType:"html",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    url=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询应用url");
             }
            })
        return url;
    }
    function getAppConfig(appId){
        var map={};
        $.ajax({
                url: "http://localhost:8888/app/config/get",
                type: "GET",
                dataType:"json",
                async : false,
                data : {
                        "appId":appId,
                },
                success:function(data){
                    map=data;
                },
             error:function(){
                console.log("服务器连接失败，无法查询应用配置");
             }
            })
        return map;
    }

    function getHeatmap(appId){
        var list=new Array();
        $.ajax({
                url: "http://localhost:8888/heatmap/get",
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
                console.log("服务器连接失败，无法查询热图信息");
             }
            })
        return list;
    }

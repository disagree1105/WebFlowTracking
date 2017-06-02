function getBrowserInfo()
{
var agent = navigator.userAgent.toLowerCase() ;

var regStr_ie = /msie [\d.]+;/gi ;
var regStr_ff = /firefox\/[\d.]+/gi
var regStr_chrome = /chrome\/[\d.]+/gi ;
var regStr_saf = /safari\/[\d.]+/gi ;
var regStr_edge = /edge\/[\d.]+/gi;

var browser;
//IE
if(agent.indexOf("msie") > 0)
{
browser=agent.match(regStr_ie) ;
}

//firefox
if(agent.indexOf("firefox") > 0)
{
browser=agent.match(regStr_ff) ;
}

//Chrome
if(agent.indexOf("chrome") > 0)
{
browser=agent.match(regStr_chrome) ;
}

//Safari
if(agent.indexOf("safari") > 0 && agent.indexOf("chrome") < 0)
{
browser=agent.match(regStr_saf) ;
}

//edge
if(agent.indexOf("edge") > 0)
{
browser=agent.match(regStr_edge) ;
}
browser=browser+"";
return browser.substring(0,browser.indexOf('/'));
}

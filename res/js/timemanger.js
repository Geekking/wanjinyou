 function nextImg(type){
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            document.getElementById("img_url").src=xmlhttp.responseText;
            
        }
    }
    xmlhttp.open("POST","hello.html",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    var img_url = document.getElementById("img_url").src;
    
    var postStr = "src="+img_url+"&label="+type;
    xmlhttp.send(postStr);
}
function keyHandler(event){
    var keycode;
    
    if(window.event) // IE 浏览器
    {
        //alert('ie');
        keycode = event.keyCode;
    }
    else if(event.which) // Netscape/Firefox/Opera浏览器
    {
        //alert('firefox ');
        keycode = event.which;
    }
}


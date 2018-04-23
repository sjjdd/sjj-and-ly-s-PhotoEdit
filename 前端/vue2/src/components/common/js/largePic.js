//展示
function showBigPic(filepath) {

    //将文件路径传给img大图
    document.getElementById('pre_view').src = filepath;
    //获取大图div是否存在
    var div = document.getElementById("bigPic");
    if (!div) {
        return;
    }
    //如果存在则展示
    document.getElementById("bigPic").style.display="block";
    //获取鼠标坐标
    var intX = window.event.clientX;
    var intY = window.event.clientY;
    //设置大图左上角起点位置
    div.style.left = intX +10+ "px";
    div.style.top = intY + 10+"px";
}

//隐藏
function closeimg(){
    document.getElementById("bigPic").style.display="none";
}

export {  
        largePic 
}  
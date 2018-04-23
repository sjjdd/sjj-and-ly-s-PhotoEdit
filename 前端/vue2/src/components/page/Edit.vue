<template>
  <div>
<body height="100%" width="100%">

  <section id="content">

        <h3>画布</h3>


    <div>
      <p>
        <label>Repeat pattern?</label>
        <input type="checkbox" id="img-repeat" checked>
      </p>
      <p>
        <label>Pattern image width</label>
        <input type="range" min="50" max="1000" value="100" id="img-width">
      </p>
      <p>
        <label>Pattern left offset</label>
        <input type="range" min="0" max="500" value="0" id="img-offset-x">
      </p>
      <p>
        <label>Pattern top offset</label>
        <input type="range" min="0" max="500" value="0" id="img-offset-y">
      </p>
      <br>
      <p>
        <label>Pattern image angle</label>
        <input type="range" min="-90" max="90" value="0" id="img-angle">
      </p>
      <p>
        <label>Pattern image padding</label>
        <input type="range" min="-50" max="50" value="0" id="img-padding">
      </p>
    </div>

<canvas id="c" width="500" height="500" style="border:10px solid #ccc"></canvas>

  </section>

</body>
</div>
</template>

<script>
import canvas from '../common/js/canvas'
    var token =  localStorage.getItem('token')
    export default {
     
      data:{
        imgUrl:''
      },
       methods: {
        showPicture(){
          this.imgUrl = localStorage.getItem("url");
        },
       send(){
            this.$axios({
                 method:"post",
                 url:"/ptEditor/picture/uploadImage",
                 headers:{'token': token},
                 responseType:'json',
                  }).then(function(response){
                      console.log(response)
                  }).catch(function(error){
                      console.log(error)
                  })
              return false
            }

       },
       ptdrawBrush(value){
        drawBrush(value)
      },
      saveimg(){
        
      },
      clearimg(){

      }
     }
</script>

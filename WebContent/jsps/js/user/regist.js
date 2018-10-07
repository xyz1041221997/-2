			function change(){
						//alert(111);
					    var img = document.getElementById("pic");
					    img.src="/goods/picture?time="+new Date().getTime();
					}
		    function  showerror(lab){
			    var tet = lab.text();
			    if(!tet){
			    	lab.css("display","none");//无内容隐藏
			    }else{
			    	lab.css("display","");//有内容显现
			    }
		  }
          $(function(){
        	   $("#registBut").hover(
        		   function(){
        			   $("#registBut").attr("src","/goods/images/regist2.jpg");
        		   },	   
        		    function(){
        		    	$("#registBut").attr("src","/goods/images/regist1.jpg");
        		    }
        	   )
        	   	 $("label").each(function(){
        			 showerror($(this));
        		 })
        		 //获得焦点是label标签内容为空
        		 $("input").focus(function(){
        			 //alert($(this).attr("id"));
        			var id =  $(this).attr("id") + "Error";
        			$("#"+id).text("");
        			showerror($("#"+id));
        		 })
        		 //失去焦点验证
        		 $("input").blur(function(){
        			// alert(11);
        			 var fun = $(this).attr("id")+"Examine()";
        			 //eval(alert(111));执行js代码
        			 eval(fun);
        		 })
          })    
          //登入name校验
            /**
        	   * 1非空校验
        	   * 2长度校验
        	   * 3用户名是否存在校验
        	   * @returns
        	   */
          function inputnameExamine(){   	  
        	  var text = $("#inputname").val();
        	  if(!text){
        		  $("#inputnameError").text("用户名不能为空");    
        		  showerror($("#inputnameError"));
        	    return false;
        	  }
        	  if(!(text.length>3&&text.length<20)){
        		  $("#inputnameError").text("用户名长度只能在3~20");
        		  showerror($("#inputnameError"));
        		 return false;
        	  }     
        	  /**
        	   * 给servlet发送异步请求
        	   */  	  
        	  $.ajax({
        		 url:"/goods/UserServlet",
        		 data:{method:"ajaxUsername",username:text},//传入的数据服务器可以通过request获得
         	    async:true,//是否异步
         	    cache:false,//是否服务器缓冲
         	    type:"post",//发送请求的方法
         	    dataType:"json",//传回数据的格式
        		 success:function(result){
        			 if(result){
        				 $("#inputnameError").text("用户名已存在");
               		  showerror($("#inputnameError"));
               		 return false;
        			 }
        		 }
        	 })
        	  return true;
          }
          //密码长度校验
          function inputpswExamine(){
        	  var text= $("#inputpsw").val();
        //	  console.log(text);
        	  if(!text){
        		  $("#inputpswError").text("密码不能为空");  
        		  showerror($("#inputpswError"));
        		return false;
        	  }        	  
        	  if(!(text.length>3&&text.length<20)){
        		  $("#inputpswError").text("密码长度只能在3~20");
        		  showerror($("#inputpswError"));
        		 return false;
        	  }
        	
        	  return true;
          }
          //确认密码校验
          function inputpsw2Examine(){
        	  var text1 = $("#inputpsw").val();
        	  var text2 = $("#inputpsw2").val();
        	//  console.log(text1+" "+text2);
        	  if(!text2){
        		  $("#inputpsw2Error").text("确认密码不能为空");
        		  showerror( $("#inputpsw2Error"));
        		  return false;
        	  }
        	  if(text2!=text1){
        		  $("#inputpsw2Error").text("前后两次密码不同");
        		  showerror( $("#inputpsw2Error"));
        		  return false;
        	  }
        	  return true;
          }
          //邮箱校验
          function inputmailExamine(){
        	  var text = $("#inputmail").val();
        	  var re= /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
        	  if(!text){
        		  $("#inputmailError").text("邮箱格式不能为空");
        		  showerror( $("#inputmailError")); 
        		  return false;
        	  }	  
        	  if(!re.test(text)){
        		  $("#inputmailError").text("邮箱格式不服标准");
        		  showerror( $("#inputmailError"));
        		  return false;
        	  }
        	  $.ajax({
         		 url:"/goods/UserServlet",
         		 data:{method:"ajaxEmail",email:text},//传入的数据服务器可以通过request获得
          	    async:true,//是否异步
          	    cache:false,//是否服务器缓冲
          	    type:"post",//发送请求的方法
          	    dataType:"json",//传回数据的格式
         		 success:function(result){
         			 if(result){
         				 $("#inputmailError").text("邮箱已使用");
                		  showerror($("#inputmailError"));
                		 return false;
         			 }
         		 }
         	 })
        	  return true;
          }
          //验证码校验
          function inputcodeExamine(){
        	  var text = $("#inputcode").val();
        	  if(text.length!=4){
        		  $("#inputcodeError").text("长度不符合");
        		  showerror( $("#inputcodeError"));
        		  return false;
        	  }
        	  $.ajax({
        		  url:"/goods/UserServlet",
        		  type:"post",
        		  data:{method:"varifycode",code:text},
        		  dataType:"json",
        		  cache:false,
        		  async:true,
        		  success:function(result){
        			 // alert(11);
          			 if(!result){
          				 $("#inputcodeError").text("验证码不正确");
                 		  showerror($("#inputcodeError"));
                 		 return false;
          			 }
          		 }
        	  })
        	  return true;
          }


jQuery(document).ready(function() {
	
    /*
        Fullscreen background
    */
    $.backstretch("assets/img/backgrounds/1.jpg");
    
    $('#top-navbar-1').on('shown.bs.collapse', function(){
    	$.backstretch("resize");
    });
    $('#top-navbar-1').on('hidden.bs.collapse', function(){
    	$.backstretch("resize");
    });
    
    /*
        Form
    */
    $('.registration-form fieldset:first-child').fadeIn('slow');
    
    $('.registration-form input[type="text"], .registration-form input[type="password"], .registration-form textarea').on('focus', function() {
    	$(this).removeClass('input-error');
    });


	//ajax nickname
	$("#form-nickname").blur(function(){
		if($(this).val() == "" ){
			$("#form-nickname-warn").css('display','inline');
			$("#form-nickname-warn").css('color','red');
			return;
		}


		htmlobj=$.ajax({
			type:"GET",
			url:"/VocabularyTrainer/register",
			data:{
				nickname:$("#form-nickname").val(),
				password:$("#form-password").val(),
				clientToken:$("#form-token").val(),
			},

			//the defaful callback parameter tpye is responseText
			success: function (resp) {

				arr=resp.split(',');
				//alert(arr[1]);
				if(arr[1] == "invalid"){

					$("#form-nickname-warn").css('display','inline');
					$("#form-nickname-warn").css('color','red');
					$("#form-token").val(arr[0]);

				} else if(arr[1] ="valid"){
					$("#form-token").val(arr[0]);
					$("#form-nickname-warn").css('display','none');
				}
			},
			error: function () {
				alert("exception!")
			}

		});
	});

	//check password
	$("#form-repeat-password").blur(function () {
		if($("#form-password").val() != $("#form-repeat-password").val() ){
			$("#form-password-warn").css('display','inline');
			$("#form-password-warn").css('color','red');
		}else{
			$("#form-password-warn").css('display','none');
		}
	})

    // next step
    $('.registration-form .btn-next').on('click', function() {
    	var parent_fieldset = $(this).parents('fieldset');
    	var next_step = true;
    	
		// for null
    	parent_fieldset.find('input[type="text"], input[type="password"], textarea').each(function() {
    		if( $(this).val() == "" ) {
    			$(this).addClass('input-error');
    			next_step = false;
    		}
    		else {
    			$(this).removeClass('input-error');
    		}
    	});
		
		//for passwrod
		if($("#form-password").val() != $("#form-repeat-password").val() ){
			$(this).addClass('input-error');
			next_step = false;
		}

		//for check nickname
		if($("#form-nickname-warn").css("display") == "inline"){
			$(this).addClass('input-error');
			next_step = false;
		}
    	
    	if( next_step ) {
    		parent_fieldset.fadeOut(400, function() {
	    		$(this).next().fadeIn();
	    	});
    	}
    	
    });
    
    // previous step
    $('.registration-form .btn-previous').on('click', function() {
    	$(this).parents('fieldset').fadeOut(400, function() {
    		$(this).prev().fadeIn();
    	});
    });
    
    // submit
    $('.registration-form').on('submit', function(e) {
    	
    	$(this).find('input[type="text"], input[type="password"], textarea').each(function() {
    		if( $(this).val() == "" ) {
    			e.preventDefault();
    			$(this).addClass('input-error');
    		}
    		else {
    			$(this).removeClass('input-error');
    		}
    	});
    	
    });
    
    
});

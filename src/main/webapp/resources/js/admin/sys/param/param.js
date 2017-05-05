var main = {
	config : { 
		getInfo : base + '/sysParam_getBean.do',
		doSaveUrl : base + '/sysParam_save.do',
		form : '.byy-form'
	},
	form : {
		bindEvent : function(){
			//1.提交按钮绑定事件
            $('#submit').on('click',main.form.save);
			//2.清空按钮绑定事件
            $('#clear').on('click',main.form.clear);
            //3.绑定上传事件
			var uploader_Excel = byy.upload.create({
    			auto : false,  
				// swf文件路径
    			swf: base + 'plugins/webuploader/Uploader.swf',
    			// 文件接收服务端。
    			server : base+'/FileUpload',
    			fileNumLimit : 1,
    			chunked : true,
    			// 选择文件的按钮。可选。
    			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
    			pick: {
    				id : '#uploadimageDiv',
    				multiple  : false,
         			innerHTML : '<span class="uploadimage byy-btn small pull-right" style="cursor:pointer;padding-top: 0px;" >图片上传</span>'
    			},
		       // 只允许选择图片文件。  
		       accept: {  
		           title: 'Images',  
		           extensions: 'gif,jpg,jpeg,bmp,png',  
		           mimeTypes: 'image/jpg,image/jpeg,image/png'  
		       },  
		       method:'POST'
    		});  
			uploader_Excel.on('fileQueued',function( file ) {
			 
			    var $li = $(".excel-row");
			    $li.attr('id',file.id);
	            uploader_Excel.upload(file);
    		});
			uploader_Excel.on( 'uploadSuccess', function( file ,response) {
    			var $target = $('#'+file.id);
    			//添加返回值，暂时..
    			var object= response[0];
    			var filename = object.filename || '',
    					fileSize = object.size || 0,
    					type = '.'+object.type;
    			$('#picPath').val(filename);
				$("#IconThum").attr( 'src', base+filename );
				//清空队列
				uploader_Excel.reset();
    		});
		},
		form : function(){
			main.form.validator();
			main.form.loadData();
		},
		loadData : function(){
			var listIndex = byy.win.load(1);
			$.ajax({
				url : main.config.getInfo,
				type : 'POST',
				data : {
					//id :  id
				},
				dataType : 'json',
				success : function(res){
					byy.win.close(listIndex);
					var resobj = byy.json(res);
					//处理图标
					if(resobj.picPath!=null&&resobj.picPath!=''){
		    			$("#IconThum").attr( 'src',base+resobj.picPath);
		    		}else{
		    			$("#IconThum").attr( 'src',base+'/plugins/ueditor/dialogs/attachment/images/image.png');
		    		}	
				    byy('form').setValues(resobj);
				}
			});
		},
		save : function(){
			var searchData = byy('.byy-form').getValues();
			if($(main.config.form).valid()){
				//提交的时候将提交按钮禁用，防止重复提交
				$('#submit').off('click').addClass('disabled');
				var paramKey = [];
				var paramValue = [];
				for(var key in searchData){
					paramKey.push(key);
					paramValue.push(searchData[key]);
				}
		    	$.ajax({
					url : main.config.doSaveUrl,
					type : 'POST',
					data : {paramKey:paramKey.join(','),paramValue:paramValue.join(',')},
					dataType : 'json',
					success : function(res){
						var resobj = byy.json(res);
						parent.byy.win.msg(resobj.msg,{shift:-1},function(){
							$('#submit').on('click',main.form.save).removeClass('disabled');
						});
					}
				});
			}
		},
		validator : function() {
			var rules = {
				sysName : {
					required : true,
					maxlength : 100
				},
				rightCopyInfo : {
					required : true
				},
				tel : {
					phone : true
				}
			};
			return byy(main.config.form).validate({
				rules : rules
			});
		},
		clear : function(){
           main.form.loadData();
        }
	}	
};

byy.require(['jquery','win','upload','validator'],function(){
	var h = location.href.toLowerCase();
	byy('.byy-breadcrumb').breadcrumb();
	main.form.bindEvent();
	main.form.form(); 
});
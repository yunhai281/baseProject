/***
 * @Name byy page 分页组件
 * @Author lixun
 * @since 2017年2月4日 13:51:25
 * @version 1.0.1 修复下拉框的bug
 ***/

byy.define(function(exports){
  "use strict";

  function byypage(options){
    var skin = 'byypagecss';
    new Page(options);
  }

  var doc = document, id = 'getElementById', tag = 'getElementsByTagName',_esMap ={},_bsMap = {};
  var index = 0,tempIncrement = 0, Page = function(options){
    var that = this;
    var conf = that.config = options || {};
    conf.item = index++;
    if(options.selector in _bsMap){}else{
      _bsMap[options.selector] = options.pagesize;
      _esMap[options.selector] = false;
    }
    that.render(true);
  };

  Page.on = function(elem, even, fn){
    elem.attachEvent ? elem.attachEvent('on'+ even, function(){
      fn.call(elem, window.even); //for ie, this指向为当前dom元素
    }) : elem.addEventListener(even, fn, false);
    return Page;
  };

  //判断传入的容器类型
  Page.prototype.type = function(){
    var conf = this.config;
    if(typeof conf.selector === 'object'){
      return conf.selector.length === undefined ? 2 : 3;
    }
  };

  //分页视图
  Page.prototype.view = function(){
    var that = this, conf = that.config, view = [], dict = {};
    conf.pageArray = byy.isArray(conf.pageArray)  ? conf.pageArray : false;
    conf.curr = (conf.curr|0) || 1;
    conf.total = conf.total||0;
    conf.pagesize = conf.pagesize|| behavior_page_rows ||10;
    conf.pages = (conf.total == 0 || conf.pagesize < 1 ) ? 0 : (conf.total % conf.pagesize == 0 ? (conf.total / conf.pagesize) : parseInt(((conf.total / conf.pagesize)+1),10));//总页数，根据总数和每页的条数进行计算
    conf.showTotal = 'showTotal' in conf ? (conf.showTotal === true ? true : false) : false;
    conf.increment = 'increment' in conf ? (conf.increment|0) : 5;
    tempIncrement = conf.increment;
    conf.first = 'first' in conf ? conf.first : '&#x9996;&#x9875;';
    conf.last = 'last' in conf ? conf.last : '&#x672B;&#x9875;';
    conf.prev = 'prev' in conf ? conf.prev : '&#x4E0A;&#x4E00;&#x9875;';
    conf.next = 'next' in conf ? conf.next : '&#x4E0B;&#x4E00;&#x9875;';
    //if(conf.pages <= 1 && !_esMap[conf.selector]){
    //如果页码等于1也显示，页码下拉框。
    if(conf.pages <=0 && !_esMap[conf.selector]){
      return '';
    }
    if(conf.increment > conf.pages){
      conf.increment = conf.pages;
    }
    //判断是否显示总数量
    if(conf.showTotal === true){
      view.push('<span class="byy-page-text" style="background-color:transparent;">共'+(conf.total||0)+'条</span>');
    }
    
    //判断是否显示修改页码数下拉框
    if(conf.pageArray.length > 0){
      view.push('<span class="byy-page-text" style="background-color:transparent;"><select class="byy-page-select">'+
        (function(arr){
          return arr.map(function(ele){
            return '<option value='+ele+' '+(conf.pagesize == ele ? 'selected' : '')+'>'+ele+'</option>';
          }).join('');
        })(conf.pageArray)
      +'</select></span>');
    }

    //计算当前组
    dict.index = Math.ceil((conf.curr + ((conf.increment > 1 && conf.increment !== conf.pages) ? 1 : 0))/(conf.increment === 0 ? 1 : conf.increment));
    
    //当前页非首页，则输出上一页
    if(conf.curr > 1 && conf.prev){
      view.push('<a href="javascript:;" class="byy-page-prev" data-page="'+ (conf.curr - 1) +'">'+ conf.prev +'</a>');
    }
    
    //当前组非首组，则输出首页
    if(dict.index > 1 && conf.first && conf.increment !== 0){
      view.push('<a href="javascript:;" class="byy-page-first" data-page="1"  title="&#x9996;&#x9875;">'+ conf.first +'</a><span>&#x2026;</span>');
    }
    
    //输出当前页组
    dict.poor = Math.floor((conf.increment-1)/2);
    dict.start = dict.index > 1 ? conf.curr - dict.poor : 1;
    dict.end = dict.index > 1 ? (function(){
      var max = conf.curr + (conf.increment - dict.poor - 1);
      return max > conf.pages ? conf.pages : max;
    }()) : conf.increment;
    if(dict.end - dict.start < conf.increment - 1){ //最后一组状态
      dict.start = dict.end - conf.increment + 1;
    }
    for(; dict.start <= dict.end; dict.start++){
      if(dict.start === conf.curr){
        view.push('<span class="byy-page-curr"><em class="byy-page-em" '+ (/^#/.test(conf.skin) ? 'style="background-color:'+ conf.skin +';"' : '') +'></em><em>'+ dict.start +'</em></span>');
      } else {
        view.push('<a href="javascript:;" data-page="'+ dict.start +'">'+ dict.start +'</a>');
      }
    }
    
    //总页数大于连续分页数，且当前组最大页小于总页，输出尾页
    if(conf.pages > conf.increment && dict.end < conf.pages && conf.last && conf.increment !== 0){
       view.push('<span>&#x2026;</span><a href="javascript:;" class="byy-page-last" title="&#x5C3E;&#x9875;"  data-page="'+ conf.pages +'">'+ conf.last +'</a>');
    }
    
    //当前页不为尾页时，输出下一页
    dict.flow = !conf.prev && conf.increment === 0;
    if(conf.curr !== conf.pages && conf.next || dict.flow){
      view.push((function(){
        return (dict.flow && conf.curr === conf.pages) 
        ? '<span class="byy-page-nomore" title="&#x5DF2;&#x6CA1;&#x6709;&#x66F4;&#x591A;">'+ conf.next +'</span>'
        : '<a href="javascript:;" class="byy-page-next" data-page="'+ (conf.curr + 1) +'">'+ conf.next +'</a>';
      }()));
    }

    return '<div class="byy-content-box byy-page byy-page-'+ (conf.skin ? (function(skin){
      return /^#/.test(skin) ? 'molv' : skin;
    }(conf.skin)) : 'default') +'" id="byy-page-'+ that.config.item +'">'+ view.join('') + function(){
      return conf.skip 
      ? '<span class="byy-page-total">&#x5230;&#x7B2C; <input type="number" min="1" onkeyup="this.value=this.value.replace(/\\D/, \'\');" value="'+ conf.curr +'" class="byy-page-skip"> &#x9875; '
      + '<button type="button" class="byy-page-btn">&#x786e;&#x5b9a;</button></span>' 
      : '';
    }() +'</div>';
  };

  //跳页
  Page.prototype.callback = function(elem){
    if(!elem) return;
    var that = this, conf = that.config, childs = elem.children;
    var btn = elem[tag]('button')[0];
    var input = elem[tag]('input')[0];
    var select = elem[tag]('select')[0];
    for(var i = 0, len = childs.length; i < len; i++){
      if(childs[i].nodeName.toLowerCase() === 'a'){
        Page.on(childs[i], 'click', function(){
          var curr = this.getAttribute('data-page')|0;
          conf.curr = curr;
          conf.callback && conf.callback(conf);
          that.render();
          
        });
      }
    }
    if(btn){
      Page.on(btn, 'click', function(){
        var curr = input.value.replace(/\s|\D/g, '')|0;
        if(curr && curr <= conf.pages){
          conf.curr = curr;
          that.render();
        }
      });
    }
    if(select){
      Page.on(select,'change',function(){
        try{
          conf.pagesize = parseInt(select.value,10);
          conf.pagesize > _bsMap[conf.selector] ? _esMap[conf.selector] = true : _esMap[conf.selector] = false;
        }catch(e){}
        conf.increment = tempIncrement;
        conf.curr = 1;
        conf.callback && conf.callback(conf);
        Page.prototype.behavior(conf.pagesize);
      });
    }
  };

  //添加用户操作习惯(每页显示条数)
  Page.prototype.behavior = function(pagesize){
	  $.ajax({	   
			url : "userBehavior_addOrUpdate.do",
			type : 'POST',
			data : {
				behaviorType:'1',
				behavior:pagesize
			},
			dataType : 'json',
			success : function(res){
				
			}
		});
  };
  //渲染分页
  Page.prototype.render = function(load){
    var that = this, conf = that.config, type = that.type();
    var view = that.view();
    //将相关的配置放在page元素中，用于其他的时候获得调用
    if(type === 2){
      $(conf.selector).html(view);
      $(conf.selector).attr('page-curr',conf.curr);
      $(conf.selector).attr('page-size',conf.pagesize);
    } else if(type === 3){
      conf.selector.html(view);
      conf.selector.data('obj',conf);
      $(conf.selector).attr('page-curr',conf.curr);
      $(conf.selector).attr('page-size',conf.pagesize);
    } else {
      $(conf.selector).html(view);
      $(conf.selector).data('obj',conf);
      $(conf.selector).attr('page-curr',conf.curr);
      $(conf.selector).attr('page-size',conf.pagesize);
    }
    

    that.callback(doc[id]('byy-page-' + conf.item));
    if(conf.hash && !load){
      location.hash = '!'+ conf.hash +'='+ conf.curr;
    }
  };
  
  exports('page', byypage);

});
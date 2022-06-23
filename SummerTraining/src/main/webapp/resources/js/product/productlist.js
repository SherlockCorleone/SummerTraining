/**
 * 
 */
 $(function(){
    getlist();
    
    function getlist(){
    	$.ajax({
    		url:"/SummerTraining/shopadmin/getproductlist?pageSize=10&pageIndex=1",
    		type:"get",
    		dataType:"json",
    		success:function(data){
    			if(data.success){
    				var productList=data.productList;
    				var html='';
    				productList.map(function(item,index){
    					var text="下架";
    					var status=0;
    					//判断商品状态，给出相反的动作
    					if(item.status==0){
    						text="上架";
    						status=1;
    					}
    					html+='<div class="row row-product">'
    					    	+'<div class="col-33">'+item.productName+'</div>'
    					    	+'<div class="col-33">'+item.priority+'</div>'
    					    	+'<div class="col-33">'
    					    	+'<a class="update" href="#" data-id="'+item.productId+'" data-status="'+item.status+'">编辑</a>'
    					    	+'<a class="changeStatus" href="#" data-id="'+item.productId+'" data-status="'+status+'">'+text+'</a>'
    					    	+'<a class="overview" href="#" data-id="'+item.productId+'" data-status="'+item.status+'">详情</a>'
    					    	+'</div></div>'
    					$('.product-wrap').html(html);
    					    	
    				});
    			}else{
    				$.toast(data.errMsg);
    			}
    		}
    	});
    }
    $('.product-wrap').on('click','a',function(e){
    	var target=$(e.currentTarget);
    	if(target.hasClass('update')){
    		window.location.href='/SummerTraining/shopadmin/productupdate?productId='+e.currentTarget.dataset.id;
    	}else if(target.hasClass('changeStatus')){
    		changeItemStatus(e.currentTarget.dataset.id,e.currentTarget.dataset.status);
    	}else if(target.hasClass('overview')){
    		window.location.href='/SummerTraining/shopadmin/productdetail?productId='+e.currentTarget.dataset.id;
    	}
    });
    
    function changeItemStatus(id,status){
    	var product={};
    	product.productId=id;
    	product.status=status;
    	$.confirm('确定修改商品上下架情况吗？',function(){
    		$.ajax({
    			url:'/SummerTraining/shopadmin/updateproduct',
    			type:'POST',
    			data:{
    				productStr:JSON.stringify(product),
    				changeStatus:true
    			},
    			dataType:'json',
    			success:function(data){
    				if (data.success) {
						$.toast('提交成功');
						getlist();
					} else {
						$.toast('提交失败:' + data.errMsg);
					}
    			}
    		});
    	});
    }
    
 });
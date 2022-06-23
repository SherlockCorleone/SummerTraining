/**
 * 
 */
 $(function(){
    getlist();
    
    
 	function getlist(e){
 	$.ajax({
 		url:"/SummerTraining/shopadmin/getshoplist",
 		type:"get",
 		dataType:"json",
 		success:function(data){
 			if(data.success){
 				handleList(data.shopList);
 				handleUser(data.owner);
 			}else {
				$.toast('获取商铺列表失败:' + data.errMsg);
				$('#captcha_img').click();
			}
 		}
 	});
 	}
 	
 	function handleUser(data){
 		$('#user-name').text(data.userName);
 	}
 	
 	function handleList(data){
 	 var html='';
 	 data.map(function(item,index){
 	 	html+='<div class="row row-shop"><div class="col-40">'
 	 		+item.shopName+'</div><div class="col-40">'
 	 		+shopStatus(item.status)
 	 		+'</div><div class="col-20">'
 	 		+goShop(item.status,item.shopId)+'</div></div>';
 	 });
 	 $('.shop-wrap').html(html);
 	}
 	
 	function shopStatus(status){
 		if(status==0){
 			return '审核中';
 		}else if(status==-1){
 			return '店铺已被BAN';
 		}else if(status==1){
 			return '店铺正常';
 		}else {
 			return '未知状态，请联系工作人员！';
 		}
 	}
 	function goShop(status,id){
 		if(status==1){
 			return '<a href="/SummerTraining/shopadmin/shopmanagement?shopId='+id+'">进入</a>';
 		}else{
 			return '';
 		}
 	}
 });
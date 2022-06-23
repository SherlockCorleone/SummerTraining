/**
 * 
 */
 $(function(){
 	var shopId=getQueryString('shopId');
 	var shopInfoUrl='/SummerTraining/shopadmin/getshopmanageinfo?shopId='+shopId;
 	$.getJSON(shopInfoUrl,function(data){
 		if(data.redirect){
 		window.location.href=data.url;
 		}else{
 			if(data.shopId!=undefined&&data.shopId!=null){
 				shopId=data.shopId
 			}
 			$('#shopInfo').attr('href','/SummerTraining/shopadmin/shopupdate?shopId='+shopId);
 		}
 	})
 })
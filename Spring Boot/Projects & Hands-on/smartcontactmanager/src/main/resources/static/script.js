const search=()=>{
    //console.log("search");
    let query=document.getElementById('search-input').value; //getting query input
    let box=document.getElementById('search-result');
     box.style.display = 'none'; //setting search box to none by default
    if(!query==''){
    //console.log(query);
    
        let url=`http://localhost:8080/user/search/${query}`
        //using promise then catch to do operations as per success failure recieved from backend/controller
        fetch(url).then((response)=>{
            return response.json();  
        }).then((data)=>{
            //console.log(data);
            let text=`<div class='list-group'>`;
            data.forEach(contact => {
                text+=`<a href='/user/contact/${contact.cId}' class='list-group-item list-group-action'>${contact.name}</a>`
            });
            text+=`</div>`;
            box.innerHTML=text;            
            box.style.display = 'inline';
        });
    }
};

const payment=()=>{
    let amount=document.getElementById('payment').value; //getting amount entered
    console.log(amount);
    if(amount=='' || amount==null){
        alert('amount invalid');
    }
    let url=`http://localhost:8080/user/createOrder`;
    
    fetch(url, { //fetching a post response with then catch for success error
    method: 'POST', // *GET, POST, PUT, DELETE, etc.
    mode: 'cors', // no-cors, *cors, same-origin
    cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
    credentials: 'same-origin', // include, *same-origin, omit
    headers: {
      'Content-Type': 'application/json'
      // 'Content-Type': 'application/x-www-form-urlencoded',
    },
    redirect: 'follow', // manual, *follow, error
    referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
    body: JSON.stringify({amount:amount}) // body data type must match "Content-Type" header
  }).then((response)=>{
	if(response.ok){
	return response.json();
	}
	else{
		throw Error(response.status);
	}
}).then((response)=>{
	console.log(response);
	let options={ //this var is passed as parameter to razorpay obj, do import razorpay script in the base html file
		key: 'rzp_test_BNKwffUSPA4Af0',
		amount:	response.amount,
		currency: 'INR',
		name: 'Donation',
		description: 'payment test',
		order_id: response.id,
		handler: function(response){
			updatePaymentFinalStatusOnServer(response.razorpay_payment_id, response.razorpay_order_id, 'paid'); //this function saves the final payment status on server
			console.log(response.razorpay_payment_id);
			console.log(response.razorpay_order_id);
			console.log(response.razorpay_signature);
					} //this handler will run after its passed to razorpay obj and response is recieved
	};
	let rzp=new Razorpay(options); //creating razorpay obj and passing obj
	rzp.on('payment.failed', function (response){ //error msg handling
        alert(response.error.code);
        alert(response.error.description);
        alert(response.error.source);
        alert(response.error.step);
        alert(response.error.reason);
        alert(response.error.metadata.order_id);
        alert(response.error.metadata.payment_id);
});

/*
document.getElementById('payButton').onclick = function(e){ //changing donate button to final payment link
    rzp.open(); //this open the razorpay payment page
    e.preventDefault(); //prevents default actions
};
*/
	rzp.open(); //this opens the razorpay payment page
}).catch((error)=>{
	console.log(error);
});

};

function updatePaymentFinalStatusOnServer(payment_id, order_id, status){
	    let url=`http://localhost:8080/user/updateOrder`;
    
    fetch(url, { //fetching a post response with then catch for success error
    method: 'POST', // *GET, POST, PUT, DELETE, etc.
    mode: 'cors', // no-cors, *cors, same-origin
    cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
    credentials: 'same-origin', // include, *same-origin, omit
    headers: {
      'Content-Type': 'application/json'
      // 'Content-Type': 'application/x-www-form-urlencoded',
    },
    redirect: 'follow', // manual, *follow, error
    referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
    body: JSON.stringify({paymentId:payment_id, orderId:order_id, status:status}) // body data type must match "Content-Type" header
  }).then((response)=>{
	if(response.ok){
	return response.json();
	}
	else{
		throw Error(response.status);
	}
}).then(()=>{
	console.log("final payment status saved on server");
}).catch(()=>{
	console.log("payment succesful but no final status data saved on server");
});
}
/**
 * 
 */


var listLength = document.getElementById("listLength").value;



var endTime = new Array(listLength);
var timeLeft = new Array(listLength);

for(let i=0; i<listLength; i++){
	  endTime[i] = document.getElementById("endTime"+i).value;
	  timeLeft[i] = document.getElementById("timeLeft"+i);
}


const updateTimeLeft = function(){
	
	let distance, days, hours, minutes, seconds;
	  // Get todays date and time
	  let now = new Date().getTime();
	  
	  for(i=0; i<listLength; i++){
		  distance = endTime[i] - now;

		  // Time calculations for days, hours, minutes and seconds
		  days = Math.floor(distance / (1000 * 60 * 60 * 24));
		  hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
		  minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
		  seconds = Math.floor((distance % (1000 * 60)) / 1000);
		  
		  timeLeft[i].textContent = days + "d " + hours + "h "+ minutes + "m " + seconds + "s ";
		  
		  if (distance < 0) {
			    //clearInterval(x);
			    timeLeft[i].textContent = "EXPIRED";
			  }
	  }
};

updateTimeLeft();

// Update the count down every 1 second
var x = setInterval(updateTimeLeft, 1000);




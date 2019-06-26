/**
 * 
 */


var endTime = document.getElementById("endTimeId").value;
var timeLeft = document.getElementById("timeLeftId");



const updateTimeLeft = function(){
	
	let distance, days, hours, minutes, seconds;
	  // Get todays date and time
	  let now = new Date().getTime();
	  
	 
		  distance = endTime - now;

		  // Time calculations for days, hours, minutes and seconds
		  days = Math.floor(distance / (1000 * 60 * 60 * 24));
		  hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
		  minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
		  seconds = Math.floor((distance % (1000 * 60)) / 1000);
		  
		  timeLeft.textContent = days + "d " + hours + "h "+ minutes + "m " + seconds + "s ";
		  
		  if (distance < 0) {
			    //clearInterval(x);
			    timeLeft.textContent = "EXPIRED";
			  }
	  
};

updateTimeLeft();

// Update the count down every 1 second
var x = setInterval(updateTimeLeft, 1000);
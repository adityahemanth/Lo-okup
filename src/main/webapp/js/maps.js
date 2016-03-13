var initialLocation;
var newyork = new google.maps.LatLng(40.69847032728747, -73.9514422416687);
var browserSupportFlag =  new Boolean();

function initialize() {
  var myOptions = {
    zoom: 6,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };

   $("#lat-field").text(20.00);
   $("#lng-field").text(30.00);
  var map = new google.maps.Map(document.getElementById("map"), myOptions);

  // Try W3C Geolocation (Preferred)
  if(navigator.geolocation) {
    browserSupportFlag = true;
    navigator.geolocation.getCurrentPosition(function(position) {
      initialLocation = new google.maps.LatLng(position.coords.latitude,position.coords.longitude);
      map.setCenter(initialLocation);
    }, function() {
      handleNoGeolocation(browserSupportFlag);
    });
  }

  // Browser doesn't support Geolocation
  else {
    browserSupportFlag = false;
    handleNoGeolocation(browserSupportFlag);
  }

  function handleNoGeolocation(errorFlag) {
    if (errorFlag == true) {
      alert("Geolocation service failed.");
      initialLocation = newyork;
    } else {
      alert("Your browser doesn't support geolocation. We've placed you in Siberia.");
      initialLocation = siberia;
    }
    map.setCenter(initialLocation);
  }

 google.maps.event.addListener(map, 'click', function(event) {
  //call function to create marker

      position = event.latLng;

      //delete the old marker
      if (marker) { marker.setMap(null); }

      //create a new marker
      marker = new google.maps.Marker({ position: position, map: map});

      // have to get lat long from position

      // have some function
      // send an ajax request
      // $.get('rest/latlong/'+position.lat()+"-"+position.lng(), function(response){
        
      //   var i = response.length - 1; // 3
        
      //   for(;i>=0; i--) {
          
      //       var url = response[i].url;
      //       var $template = $("#column-template").html(); // html as string
      //       $template.replace("{url_to_replace}", url);
      //       $("#all-images").append($template);
      //   }
      // });

  });

 }
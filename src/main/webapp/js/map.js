 // This example adds a search box to a map, using the Google Place Autocomplete
// feature. People can enter geographical searches. The search box will return a
// pick list containing a mix of places and predicted search terms.

// This example requires the Places library. Include the libraries=places
// parameter when you first load the API. For example:
// <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY&libraries=places">


var position;
var geocoder; 
var bounds; 
var map = null;
var markers = [];
var input = document.getElementById('search');

function setAddress(address){
  $("#search").val(address);
}

function dosearch(){

  geocoder.geocode({'address': input.value}, function(results, status) {
       if (status === google.maps.GeocoderStatus.OK) {
          var pos = {
            lat : results[0].geometry.location.lat(),
            lng : results[0].geometry.location.lng()
          }

          performGet(pos);
          map.setCenter({lat: pos.lat, lng: pos.lng});
        }
      });
}

function performGet(position) {

    var icon = {
      url: "images/pin.svg",
      size: new google.maps.Size(109, 109),
      origin: new google.maps.Point(0, 0),
      anchor: new google.maps.Point(17, 34),
      scaledSize: new google.maps.Size(35, 35)
    };

    var pos = position;
    var rad = $("#radius").val();

    // check 
    if(rad===""){
      rad = 5;
      $("#radius").val(rad);
    }

    $.get( "http://localhost:8080/rest/latlng", {radius : rad*1000, lat : pos.lat, lng : pos.lng},
     function( data ) {
           var count = data.length - 1; 

           var wrapper = $("#photo-container");
           wrapper.empty();

           markers.forEach(function(marker) {
              marker.setMap(null);
            });

           markers=[];
           bounds = new google.maps.LatLngBounds();


           if(count === -1) {
            var $temp = $("#empty-template").html();
            wrapper.append($temp);
           }


           else {

            var $temp = $("#header-template").html();
            wrapper.append($temp); 

             for(;count >= 0; count--){
                
              var url = data[count].url;
              var desp = data[count].description;
              var title = data[count].title;
              var lc = data[count].location;
              var auth = data[count].owner;
              var latlng = new google.maps.LatLng(lc.latitude, lc.longitude);

              var $template = $("#column-template").html(); // html as string
              $template = $template.replace("{title_to_replace}",title);
              $template = $template.replace("{title_to_replace}",title);
              $template = $template.replace("{desp_to_replace}",desp);
              $template = $template.replace("{url_to_replace}",url);
              $template = $template.replace("{author_to_replace}",auth);
              $("#title1").text(data[count].title);
              wrapper.append($template);
              var marker = new google.maps.Marker({ position: latlng, map: map,  icon: icon});
              markers.push(marker);
              bounds.extend(latlng);  
             }

             map.fitBounds(bounds);

          }
    });
  }


function initAutocomplete() {
 
 if(map == null) {
  map = new google.maps.Map(document.getElementById('map'), {
    center: {lat: 32.8688, lng: -121.2195},
    zoom: 6
  });

  geocoder = new google.maps.Geocoder;
  bounds = new google.maps.LatLngBounds();

  // Create the search box and link it to the UI element.
  var searchBox = new google.maps.places.SearchBox(input);
 } 

  // Bias the SearchBox results towards current map's viewport.
  map.addListener('bounds_changed', function() {
    searchBox.setBounds(map.getBounds());
  });

  // Listen for the event fired when the user selects a prediction and retrieve
  // more details for that place.
  searchBox.addListener('places_changed', function() {
    var places = searchBox.getPlaces();

    if (places.length == 0) {
      return;
    }

    // For each place, get the icon, name and location.
    places.forEach(function(place) {
      var pos = {
        lat: place.geometry.location.lat(),
        lng: place.geometry.location.lng()
      }

      map.setCenter(pos);
      performGet(pos);
    });

  });

      // Try HTML5 geolocation.
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      var pos = {
        lat: position.coords.latitude,
        lng: position.coords.longitude
      };

      map.setCenter(pos);
      geocoder.geocode({'location': pos}, function(results, status) {
         if (status === google.maps.GeocoderStatus.OK) {
            setAddress(results[1].formatted_address);
            performGet(pos);
          }
        });
      });

    }
}



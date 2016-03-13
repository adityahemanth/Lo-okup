 <script>
            var map;
            var marker = null;
			var position;

            function initialize() {

                var mapOptions = {
                    zoom: 10,
                    disableDefaultUI: false,
                    center: new google.maps.LatLng(45.401584,-71.905861), //center on sherbrooke
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };

                map = new google.maps.Map(document.getElementById('map'), mapOptions);

                
                google.maps.event.addListener(map, 'click', function(event) {
                //call function to create marker
					
                  	position = event.latLng;
        
                    //delete the old marker
                    if (marker) { marker.setMap(null); }

                    //creer à la nouvelle emplacement
                     marker = new google.maps.Marker({ position: position, map: map});
                  
                  	// have some function
                  	// send an ajax request
                  	$.get('rest/latlong/'+position.lat()+"-"+position.lng(), function(response){
                    	
                      var i = response.length - 1; // 3
                      
                      for(;i>=0; i--) {
                        
                       		var url = response[i].url;
                        	var $template = $("#column-template").html(); // html as string
                        	$template.replace("{url_to_replace}", url);
                        	$("#all-images").append($template);
                      }
                    });

                });

            }  

            google.maps.event.addDomListener(window, 'load', initialize);
            
        </script>
        
        
        <div class="hide" id="column-template">
 <div class="col l4" style="padding: 20px">

                    <div class="card" style="overflow: hidden;">
                      <div class="card-image waves-effect waves-block waves-light">
                        <div style="overflow: hidden; height:400px;
                                        width: 100%;
                                        background-image: url('{url_to_replace}');
                                        background-size: cover;
                                        background-position: center;">
                        </div>
                      </div>
                      <div class="card-content">
                        <span class="card-title activator grey-text text-darken-4" style="text-transform: capitalize">Final Presentation<i class="material-icons right">more_vert</i></span>

                        <div class="card-action" style="padding-top: 20px;padding-bottom: 20px;margin-bottom: -20px;">
                        <a href="#"><i class="material-icons" style="padding-right: 20px;">favorite_border</i></a>
                        <a href="#"><i class="material-icons">rate_review</i></a>
                        </div>
                      </div>
                      <div class="card-reveal" style="display: none; transform: translateY(0px);">
                        <span class="card-title grey-text text-darken-4"><p>Final Presentation</p><i class="material-icons right">close</i></span>
                        <p>Here is some more information about this product that is only revealed once clicked on.</p>
                      </div>
                    </div>

                    </div>
                        </div>
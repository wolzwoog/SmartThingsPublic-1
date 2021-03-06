/**
 *  sqVS
 *
 *  Copyright 2014 Mike Maxwell
 *  Orginal update by Lee Charlton to handle volume control and refresh status.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
	metadata {
	definition (name: "sqVS", namespace: "LeeC77", author: "Lee Charlton") {
		capability "Switch"
        capability "Refresh"
       
        command "PlayerResp", ["string"]
        command "refresh"
        command "volumeUp", ["string"]
        command "volumeDown", ["string"]
        
	}

   // Don't think this is currently working enum needs defining.
   // preferences {
   //     input name: "sq", type: "enum", title: "squeezeBox", description: "Squeeze Box to Connect to", required: true
   //}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles (scale: 2){

    	standardTile("switch", "device.switch", width: 5, height: 3,  canChangeBackground: true) {
 
        		state "on", label: '${name}', action: "switch.off", icon: "https://static.thenounproject.com/png/1732216-200.png", backgroundColor: "#79b821"
        		state "off", label: '${name}', action: "switch.on", icon: "https://static.thenounproject.com/png/1732216-200.png", backgroundColor: "#ffffff"
        		// to handle the change of state.
        		state "turningOn", label:'Turning on', action: "switch.off", icon:"https://static.thenounproject.com/png/1732216-200.png", backgroundColor:"#00a0dc"
        		state "turningOff", label:'Turning off', action: "switch.on", icon:"https://static.thenounproject.com/png/1732216-200.png", backgroundColor:"#ffffff"
        	}
    }   

	standardTile("volumeUp", "device.volume", inactiveLabel: false, decoration: "flat") {
			state "up", action:"volumeUp", icon:"https://static.thenounproject.com/png/1377267-200.png"
            }
    standardTile("volumeDown", "device.volume", inactiveLabel: false, decoration: "flat") {
			state "down", action:"volumeDown", icon:"https://static.thenounproject.com/png/1377261-200.png"
            }
    standardTile("refresh", "device.switch", inactiveLabel: false, decoration: "flat") {
        state "default", label:'', action:"refresh.refresh", icon:"https://static.thenounproject.com/png/15460-200.png"
    }

    main "switch"
    details(["switch","volumeUp","volumeDown", "refresh"])
}



// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
	// TODO: handle 'switch' attribute

}

// handle commands
def on() {
	log.debug "On"
    sendEvent (name: "switch", value: "turningOn")
}

def off() {
// Always on //
	log.debug "Off"
    sendEvent (name: "switch", value: "turningOff")
}

def refresh() {
	log.debug "Refresh"
	sendEvent (name: "refresh", value: "refresh")
    sendEvent (name: "refresh", value: "done")
}
def volumeUp() {
	log.debug "Volume up"
	sendEvent (name: "volume", value: "up")
    sendEvent (name: "volume", value: "done")
}
def volumeDown() {
	log.debug "Volume down"
	sendEvent (name: "volume", value: "down")
    sendEvent (name: "volume", value: "done")
}

def PlayerResp (response){
	log.debug "Response is:${response}"

	if (response.indexOf("play")>=0){
	
    		log.debug " switched on"
    		sendEvent (name: "switch", value: "on")
             
    }
    else if (response.indexOf("power 0")>=0){
    
    		log.debug " switched off"
    		sendEvent (name: "switch", value: "off")
             
    }
}
             
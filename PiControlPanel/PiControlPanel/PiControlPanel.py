from tkinter import *
import bluetooth
import threading
import time
import json

class ControlPanel():
	ButtonStates = {
		"Button 1": False,
		"Button 2": False,
		"Button 3": False,
		"Button 4": False,	
		"Button 5": False,	
		"Button 6": False,	
	}

	def __init__(self):		
		# Construct GUI 
		self.window = Tk()				

	# Added elements to window
	def initWindow(self):
		window = self.window
		window.geometry("400x600")
		window.title("Pi Control Panel")
		btn1 = Button(window,text="Click Me",command= lambda: self.onButtonClick(1),width=20)
		btn1.grid(column=0,row=0,padx=30,pady=30)
		btn2 = Button(window,text="Click Me",command= lambda: self.onButtonClick(2),width=20)
		btn2.grid(column=1,row=0,padx=30,pady=30)
		btn3 = Button(window,text="Click Me",command= lambda: self.onButtonClick(3),width=20)
		btn3.grid(column=0,row=1,padx=30,pady=30)
		btn4 = Button(window,text="Click Me",command= lambda: self.onButtonClick(4),width=20)
		btn4.grid(column=1,row=1,padx=30,pady=30)
		btn5 = Button(window,text="Click Me",command= lambda: self.onButtonClick(5),width=20)
		btn5.grid(column=0,row=2,padx=30,pady=30)
		btn6 = Button(window,text="Click Me",command= lambda: self.onButtonClick(6),width=20)
		btn6.grid(column=1,row=2,padx=30,pady=30)

		# Button state sync thread
		syncT = threading.Thread(target=self.initBluetoothPanelSyncThread)
		syncT.start()

		# Start main GUI Loop
		window.mainloop()

	# Handle button click event
	def onButtonClick(self,num):
		print("Button Number " + str(num) + " was clicked")
		return 	

	# Every 250ms Transmit current button states as json string to mobile device 
	def initBluetoothPanelSyncThread(self):
		while(True):
			time.sleep(.25)		
			buttonStatesJsonStr = str(json.dumps(self.ButtonStates))
			# Transmit json string over bluetooth

class BluetoothSession():
	server_sock = None
	target_name = None
	target_address = "7C:A1:AE:78:DF:B9"

	# Find target device 
	def __init__(self,target_name):
		self.target_name = target_name
		

	def waitForConnection(self):
		# Create Bluetooth communcation socket
		server_sock=bluetooth.BluetoothSocket( bluetooth.RFCOMM )

		#port = bluetooth.get_available_port( bluetooth.RFCOMM )
		port = 15
		server_sock.bind(("",port))
		server_sock.listen(1)
		print("listening on port " + str(port))

		uuid = "1e0ca4ea-299d-4335-93eb-27fcfe7fa848"
		bluetooth.advertise_service( server_sock,name="Test",service_id=uuid)

		client_sock,address = server_sock.accept()
		print("Accepted connection from ",address)
		

	def locateTargetDeviceAddress(self):
		nearbyDevices = bluetooth.discover_devices()	
		for bdaddr in nearbyDevices:
			if(self.target_address == bdaddr):
				print("found target bluetooth device with address ", self.target_address)
			else:
				print("could not find target bluetooth device nearby")


	def closeSocket(self):
		self.client_sock.close()
		self.server_sock.close()
	

# Main routine of program 
def main():
	controlPanel = ControlPanel()

	bSession = BluetoothSession("dylan's iPhone")
	bSession.waitForConnection()

	GUIThread = threading.Thread(target=controlPanel.initWindow())
	GUIThread.start()

main()

from tkinter import *

def main():
	window = Tk()
	window.geometry("400x600")
	window.title("Pi Control Panel")	

	btn1 = Button(window,text="Click Me")
	btn1.grid(column=0,row=0)
	btn2 = Button(window,text="Click Me")
	btn2.grid(column=1,row=0)
	btn3 = Button(window,text="Click Me")
	btn3.grid(column=0,row=1)
	btn4 = Button(window,text="Click Me")
	btn4.grid(column=1,row=1)

	window.mainloop()

main()
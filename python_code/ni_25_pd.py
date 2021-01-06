import RPi.GPIO as GPIO
import sys
GPIO.setmode(GPIO.BCM)
GPIO.setup(25, GPIO.IN, pull_up_down = GPIO.PUD_DOWN)
print(GPIO.input(25))
sys.stdout.flush()
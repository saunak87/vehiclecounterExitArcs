import RPi.GPIO as GPIO
import sys
GPIO.setmode(GPIO.BCM)
GPIO.setup(23, GPIO.IN, pull_up_down = GPIO.PUD_UP)
print(GPIO.input(23))
sys.stdout.flush()
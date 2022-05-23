#Importing libraries 
#from tkinter.tix import Tree
from random import random
from selenium import webdriver
#from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
#from selenium.webdriver.support.expected_conditions import presence_of_element_located
from selenium.common.exceptions import NoSuchElementException
import time 
#import string
#import openpyxl
#import os
import csv

#Loading Selenium Webdriver 
driver= webdriver.Chrome(r'C:\Users\USER ACCOUNT\Desktop\Projek\chromedriver\chromedriver.exe')
wait = WebDriverWait(driver, 10)

#Opening Google maps 
driver.get("https://www.indorelawan.org/o/organization/search")
time.sleep(2)
file = open('Data_Base_Yayasan/indorelawan.csv', 'w', newline='')
writer = csv.writer(file)
nomor = 0
writer.writerow(['nomor','nama', 'keahlian 1', 'keahlian 2', 'lokasi'])
labels=[]
while True :
    try:
        for i in range (1,11,1):
            nomor+=1 
            nama= driver.find_element_by_xpath('//*[@id="root"]/div/section/section/div/div[1]/main/div/div[3]/div/div['+str(i)+']/div/div/div/div[2]/div[1]').text
            keahlian_1= driver.find_element_by_xpath('//*[@id="root"]/div/section/section/div/div[1]/main/div/div[3]/div/div['+str(i)+']/div/div/div/div[2]/div[2]/div[1]/div[1]/span').text
            keahlian_2=driver.find_element_by_xpath('//*[@id="root"]/div/section/section/div/div[1]/main/div/div[3]/div/div['+str(i)+']/div/div/div/div[2]/div[2]/div[1]/div[2]/span').text
            lokasi= driver.find_element_by_xpath('//*[@id="root"]/div/section/section/div/div[1]/main/div/div[3]/div/div['+str(i)+']/div/div/div/div[2]/div[2]/span').text
            print(str(nomor)+" ; "+nama+" ; "+keahlian_1+" ; "+keahlian_2+" ; "+lokasi)
            writer.writerow([str(nomor),nama,keahlian_1,keahlian_2,lokasi,telepon])
        next= driver.find_element_by_class_name('ant-pagination-next').click()
        time.sleep(5)

    except:
        file.close()
        driver.close()
        driver.quit()
        quit()

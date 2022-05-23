#Importing libraries 
#from tkinter.tix import Tree
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
driver.get("https://www.google.com/maps")
time.sleep(10)
lokasi = "Pontianak"
kata_kunci= "Organisasi Sukarelawan"
def location (lokasi):
    lokasi_tempat=driver.find_element_by_class_name('tactile-searchbox-input')
    lokasi_tempat.send_keys(lokasi)
    lokasi_tempat.send_keys(Keys.ENTER)
    time.sleep(5)
    driver.find_element_by_xpath('//*[@id="QA0Szd"]/div/div/div[1]/div[2]/div/div[1]/div/div/div[4]/div[3]/button/span').click()
def search_place(kata_kunci):
    place=driver.find_element_by_class_name('tactile-searchbox-input')
    place.send_keys(kata_kunci)
    submit = driver.find_element_by_xpath('//*[@id="searchbox-searchbutton"]')
    submit.click()

location(lokasi)
time.sleep(2)
search_place(kata_kunci)
time.sleep(3)

#Tempat simpan data  
file = open('Dataset/Yayasan_di_provinsi_{}.csv'.format(lokasi), 'w', newline='')
writer = csv.writer(file)
nomor = 0
writer.writerow(['Nomor','Nama','Label', 'Alamat', 'Telephone'])
labels=[]

while True :
    try:
        #Fungsi scroll pages
        scroll=driver.find_element_by_xpath('//*[@id="QA0Szd"]/div/div/div[1]/div[2]/div/div[1]/div/div/div[2]/div[1]/div[3]/div/a')
        scroll.click()
        for i in range (1,15):
            scroll.send_keys(Keys.PAGE_DOWN)
            time.sleep(1)
        #Fungsi mengambil data
        for i in range (3,42,2):
            nomor+=1 
            driver.find_element_by_xpath('//*[@id="QA0Szd"]/div/div/div[1]/div[2]/div/div[1]/div/div/div[2]/div[1]/div['+str(i)+']/div/a').click()
            nama= driver.find_element_by_xpath('//*[@id="QA0Szd"]/div/div/div[1]/div[2]/div/div[1]/div/div/div[2]/div[1]/div['+str(i)+']/div/div[2]/div[2]/div[1]/div/div/div/div[1]').text
            time.sleep(4)
            alamat = driver.find_element_by_xpath('//*[@id="QA0Szd"]/div/div/div[1]/div[3]/div/div[1]/div/div/div[2]/div[7]/div[1]/button/div[1]/div[2]/div[1]').text
            try : 
                label = driver.find_element_by_xpath('//*[@id="QA0Szd"]/div/div/div[1]/div[2]/div/div[1]/div/div/div[2]/div[1]/div['+str(i)+']/div/div[2]/div[2]/div[1]/div/div/div/div[4]/div/span/jsl/span[2]').text
            except NoSuchElementException:
                label = "None"
            try: 
                telephone = driver.find_element_by_xpath('//*[@id="QA0Szd"]/div/div/div[1]/div[2]/div/div[1]/div/div/div[2]/div[1]/div['+str(i)+']/div/div[2]/div[2]/div[1]/div/div/div/div[4]/div[2]/span[2]/jsl/span[2]').text
            except NoSuchElementException:
                telephone = "None"
            time.sleep(2)
            print(str(nomor)+" ; "+nama+" ; "+label+" ; "+alamat+" ; "+telephone)
            writer.writerow([str(nomor),nama.encode('utf-8'),label,alamat,telephone])
        driver.find_element_by_xpath('//*[@id="ppdPk-Ej1Yeb-LgbsSe-tJiF1e"]').click()
        time.sleep(3)
    except:
        file.close()
        driver.close()
        driver.quit()
        quit()
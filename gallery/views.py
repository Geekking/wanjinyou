from django.shortcuts import render,render_to_response
from django.http import HttpResponse
import os
import os.path 
import time
def getAPicUrl():
	rootPath = "res/Baby/Faces/"
	for parent,dirnames,filenames in os.walk(rootPath):
		for dirname in dirnames:
			pass
		for filename in filenames:
			if filename[0] == '.':
				continue
			return "/"+ parent+"/"  + filename	
	return "res/image/1.jpg"
def handleImg(src,label):
	src = src.split('/')
	src = src[3:len(src)]
	originPath = "/".join(src)
	if int(label) >=0 and int(label) <5:
		saveRoot = "res/Baby/Labeled/" + label +"/"
	else:
		return
	stamp = int(time.time()*100) 
	try:
		savePath = saveRoot + str(stamp) +"."+ src[-1].split('.')[-1]
		print originPath
		print savePath
		os.rename(originPath,savePath)
	except:
		print "conflict at " + originPath
		
	 
def hello(request):
	if request.method == 'GET':
		imageUrl = getAPicUrl()
		#print imageUrl
		return render_to_response('hello.html',{'img_url' : imageUrl})
	if  request.method == 'POST':
		label = request.POST['label']
		src  = request.POST['src']
		handleImg(src,label)
		imageUrl = getAPicUrl()
		#return render_to_response('hello.html',{'img_url':imageUrl})	
		return HttpResponse(imageUrl)
# Create your views here.

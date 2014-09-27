from django.shortcuts import render,render_to_response
from django.http import HttpResponse,HttpResponseRedirect
import os
import os.path 
import time


def setName(request):
	if request.method == 'GET':
		return render_to_response('setName.html')
	elif request.method == 'POST':
		name = request.POST['name']
		#request.session['username'] = 'ok'		
		return HttpResponseRedirect('recordTime.html')

def recordTime(request):
	if request.method == 'GET':
		return render_to_response('recordTime.html',{'username':'mude'}) 
	else:
		pass	
		#return render_to_response('hello.html',{'img_url':imageUrl})	
		#return HttpResponse(imageUrl)
# Create your views here.

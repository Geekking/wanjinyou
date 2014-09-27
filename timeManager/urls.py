from django.conf.urls import patterns, include, url
from django.contrib import admin
from timeManager.views import setName,recordTime


urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'imageClassification.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),
	url(r'index.html',setName),
	url(r'recordTime.html',recordTime),

)

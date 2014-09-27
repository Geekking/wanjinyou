from django.conf.urls import patterns, include, url
from django.contrib import admin
from gallery.views import *


urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'imageClassification.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),
	url(r'hello.*',hello),
)

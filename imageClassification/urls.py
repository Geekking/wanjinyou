from django.conf.urls import patterns, include, url
from django.contrib import admin
import gallery
import timeManager
from django.conf import settings
from django.conf.urls.static import static
urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'imageClassification.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^admin/', include(admin.site.urls)),
	url(r'^image/', include('gallery.urls')),
	url(r'^time/',  include('timeManager.urls')),
)+ static(settings.MEDIA_URL,document_root = settings.MEDIA_ROOT)

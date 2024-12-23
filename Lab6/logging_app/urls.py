from django.urls import path
from . import views

urlpatterns = [
    path('', views.index, name='index'),
    path('log/', views.log_example, name='log_example'),
    path('http-log/', views.http_log_example, name='http_log_example'),
    path('execution-time/', views.execution_time_example, name='execution_time_example'),
    path('clear-logs/', views.clear_logs, name='clear_logs'),
    path('logs/', views.get_logs, name='get_logs'),
]
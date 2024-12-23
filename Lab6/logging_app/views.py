import logging
import time
from django.http import HttpResponse
from django.shortcuts import render
import os

from lab6.settings import BASE_DIR

logger = logging.getLogger('django')

def index(request):
    return render(request, 'index.html')

def log_example(request):
    logger.debug("This is a DEBUG message")
    logger.info("This is an INFO message")
    logger.warning("This is a WARN message")
    logger.error("This is an ERROR message")
    return HttpResponse("Log messages sent to console and file!")

def http_log_example(request):
    logger.info(f"HTTP Request: {request.method} {request.path}")
    response = HttpResponse("HTTP request and response logged!")
    logger.info(f"HTTP Response: {response.content}")
    return response

def execution_time_example(request):
    start_time = time.time()
    time.sleep(1)  # Симуляция долгой операции
    execution_time = time.time() - start_time
    logger.info(f"execution_time_example executed in {execution_time:.2f}s")
    return HttpResponse("Execution time logged!")

def clear_logs(request):
    log_file = os.path.join(BASE_DIR, 'logs', 'app.log')
    try:
        open(log_file, 'w').close()
        logger.info("Log file has been cleared.")
        return HttpResponse("Log file cleared successfully!")
    except Exception as e:
        logger.error(f"Error clearing log file: {e}", exc_info=True)
        return HttpResponse(f"Failed to clear log file: {str(e)}", status=500)

def get_logs(request):
    log_file = os.path.join(BASE_DIR, 'logs', 'app.log')
    try:
        with open(log_file, 'r') as f:
            logs = f.readlines()
        return HttpResponse('<br>'.join(logs))
    except FileNotFoundError:
        return HttpResponse("Log file not found.", status=404)
from django.shortcuts import render
from django.http import HttpResponseRedirect, HttpResponseNotFound
from .models import Person
from .forms import PersonForm
from django.core.cache import cache
import logging

logger = logging.getLogger(__name__)

def index(request):
    form = PersonForm()
    people = Person.objects.all()
    logger.info("Checking cache for 'people_table'")
    cached_data = cache.get('people_table')
    if cached_data:
        logger.info("Data retrieved from cache")
    else:
        logger.info("Data not found in cache, fetching from database")
        cache.set('people_table', people, timeout=300)
    return render(request, 'index.html', {'form': form, 'people': people})

def create(request):
    if request.method == 'POST':
        form = PersonForm(request.POST)
        if form.is_valid():
            form.save()
            logger.info("Clearing cache for 'people_table'")
            cache.delete('people_table')
    return HttpResponseRedirect('/')

def edit(request, id):
    try:
        person = Person.objects.get(id=id)
        if request.method == 'POST':
            form = PersonForm(request.POST, instance=person)
            if form.is_valid():
                form.save()
                logger.info("Clearing cache for 'people_table'")
                cache.delete('people_table')
            return HttpResponseRedirect('/')
        else:
            form = PersonForm(instance=person)
            return render(request, 'edit.html', {'form': form})
    except Person.DoesNotExist:
        return HttpResponseNotFound('<h2>Person not found</h2>')

def delete(request, id):
    try:
        person = Person.objects.get(id=id)
        person.delete()
        logger.info("Clearing cache for 'people_table'")
        cache.delete('people_table')
        return HttpResponseRedirect('/')
    except Person.DoesNotExist:
        return HttpResponseNotFound('<h2>Person not found</h2>')
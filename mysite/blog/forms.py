from django import forms
from .models import Comment

class CommentForm(forms.ModelForm):
    name = forms.CharField(required=True,
                           widget=forms.TextInput(attrs={"class": "form-control", 'placeholder': 'Имя'}))
    email = forms.EmailField(required=True,
                             widget=forms.EmailInput(attrs={"class": "form-control", 'placeholder': 'Email'}))
    body = forms.CharField(required=True, widget=forms.Textarea(attrs={"class": "form-control", 'placeholder': 'Текст'}))

    class Meta:
        model = Comment
        fields = ['name', 'email', 'body']

class EmailPostForm(forms.Form):
    name = forms.CharField(max_length=25, required=True, widget=forms.TextInput(attrs={"class": "form-control mb-1", 'placeholder': 'Имя'}))
    email = forms.EmailField(required=True, widget=forms.TextInput(attrs={"class": "form-control mb-1", 'placeholder': 'E-Mail'}))
    to = forms.EmailField(required=True, widget=forms.TextInput(attrs={"class": "form-control mb-1", 'placeholder': 'Кому'}))
    comments = forms.CharField(required=False,
                               widget=forms.Textarea(attrs={"class": "form-control mb-1", 'placeholder': 'Комментарий'}))

class SearchForm(forms.Form):
    query = forms.CharField(
        widget=forms.TextInput(attrs={"class": "form-control mb-1", 'placeholder': 'Enter search term...'}))
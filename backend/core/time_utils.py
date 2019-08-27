from django.utils import timezone


def get_timestamp():
    return round(timezone.now().timestamp() * 1000)

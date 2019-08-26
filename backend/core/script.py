from users.models import AppUser

pub_id = '123'
api_token = '123'
if not AppUser.objects.filter(pub_id=pub_id, api_token=api_token).exists():
    app_user = AppUser()
    app_user.pub_id = pub_id
    app_user.api_token = api_token
    app_user.user_sign_pub_key = ''
    app_user.name = 'AppUser'
    app_user.save()
    print('add default app user success')

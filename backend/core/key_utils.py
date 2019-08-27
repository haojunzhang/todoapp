from base64 import b64decode, b64encode
from binascii import Error as binasciiError
from Crypto.Hash import SHA256
from Crypto.Cipher import PKCS1_v1_5 as Cipher_pkcs1_v1_5
from Crypto.Signature import PKCS1_v1_5 as Signature_pkcs1_v1_5
from Crypto.PublicKey import RSA


def rsa_import_key_pem(file_path):
    rsa_object = RSA.import_key(open(file_path, 'r').read())
    return rsa_object


APP_USER_SIGN_PRI_KEY = rsa_import_key_pem('keys/app_user_sign_pri.pem')
APP_USER_SIGN_PUB_KEY = rsa_import_key_pem('keys/app_user_sign_pub.pem')


def encrypt(text, pub_key):
    cipher = Cipher_pkcs1_v1_5.new(pub_key)
    cipher_text = cipher.encrypt(text.encode())
    return b64encode(cipher_text).decode()


def decrypt(text, pri_key):
    try:
        cipher_text = b64decode(text)
    except binasciiError:
        return ''
    else:
        cipher = Cipher_pkcs1_v1_5.new(pri_key)
        try:
            message = cipher.decrypt(cipher_text, None)
        except ValueError:
            return ''
        else:
            return message.decode() if message else ''


def sign(text, pri_key):
    msg_hash = SHA256.new(b64encode(text.encode()))
    return b64encode(Signature_pkcs1_v1_5.new(pri_key).sign(msg_hash)).decode("utf-8")


def verify(text, signature, pub_key):
    try:
        return Signature_pkcs1_v1_5.new(pub_key).verify(SHA256.new(b64encode(text.encode())), b64decode(signature))
    except (ValueError, TypeError):
        return False


# text = 'abc'
# e = en(text)
# d = de(e)
#
# print(f'text:{text}')
# print(f'encrypt:{e}')
# print(f'decrypt:{d}')
# print('---')
#
# s = sign(text)
# v = verify(text, s)
#
# print(f'signature:{s}')
# print(f'verify:{v}')

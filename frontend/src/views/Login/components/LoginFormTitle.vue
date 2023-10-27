<template>
  <h2 class="mb-3 text-2xl font-bold text-center xl:text-3xl enter-x xl:text-center">
    {{ getFormTitle }}
  </h2>
</template>
<script lang="ts" name="LoginFormTitle" setup>
import { LoginStateEnum, useLoginState } from './useLogin'

const { t } = useI18n()

const { getLoginState } = useLoginState()

const getFormTitle = computed(() => {
  // //{"0":"登录","1":"注册","2":"重置密码","3":"手机登录","4":"二维码登录","5":"三方授权"}
  const titleObj = {
    [LoginStateEnum.RESET_PASSWORD]: t('sys.login.forgetFormTitle'),
    [LoginStateEnum.LOGIN]: t('sys.login.signInFormTitle'),
    [LoginStateEnum.REGISTER]: t('sys.login.signUpFormTitle'),
    [LoginStateEnum.MOBILE]: t('sys.login.mobileSignInFormTitle'),
    [LoginStateEnum.QR_CODE]: t('sys.login.qrSignInFormTitle'),
    [LoginStateEnum.SSO]: t('sys.login.ssoFormTitle')
  }
  return titleObj[unref(getLoginState)]
})
</script>

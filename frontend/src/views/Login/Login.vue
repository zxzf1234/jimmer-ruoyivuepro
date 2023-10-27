<template>
  <div>
    <img :src="bg" class="wave" />
    <div class="login-container">
      <div class="img">
        <component :is="toRaw(illustration)" />
      </div>
      <!-- 右边的登录界面 -->
      <div class="login-box">
        <div class="login-form">
          <div class="login-container">
            <div class="login-box">
              <div class="login-form">
                <avatar class="avatar" />
                <Motion>
                  <h2 class="outline-none">
                    <TypeIt :cursor="false" :speed="150" />
                  </h2>
                </Motion>
                <Transition appear enter-active-class="animate__animated animate__bounceInRight">
                  <div
                    class="login-form h-full flex items-center m-auto w-[100%] @2xl:max-w-500px @xl:max-w-500px @md:max-w-500px @lg:max-w-500px"
                  >
                    <!-- 账号登录 -->
                    <LoginForm class="p-20px h-auto m-auto <xl:(rounded-3xl light:bg-white)" />
                    <!-- 手机登录 -->
                    <MobileForm class="p-20px h-auto m-auto <xl:(rounded-3xl light:bg-white)" />
                    <!-- 二维码登录 -->
                    <QrCodeForm class="p-20px h-auto m-auto <xl:(rounded-3xl light:bg-white)" />
                    <!-- 注册 -->
                    <RegisterForm class="p-20px h-auto m-auto <xl:(rounded-3xl light:bg-white)" />
                    <!-- 三方登录 -->
                    <SSOLoginVue class="p-20px h-auto m-auto <xl:(rounded-3xl light:bg-white)" />
                  </div>
                </Transition>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" name="Login" setup>
import TypeIt from '@/components/ReTpeit'
import { LoginForm, MobileForm, QrCodeForm, RegisterForm, SSOLoginVue } from './components'
import { toRaw } from 'vue'
import { bg, avatar, illustration } from './utils/static'
</script>

<style lang="scss" scoped>
@import url('./styles/login.css');
</style>

import type { App } from 'vue'
import '@imengyu/vue3-context-menu/lib/vue3-context-menu.css'
import ContextMenu from '@imengyu/vue3-context-menu'

export const setupContextMenu = (app: App<Element>) => {
  app.use(ContextMenu)
}

import ElementPlusConfig from "./ElementPlus.config.js";
import RouterConfig from "../config/route.config.js";
import PiniaConfig from "./pinia.config.js";
import HttpConfig from "@/config/http.config.js";
export default {
    /**
     *
     * @param {App} app
     */
    install(app){
        app.use(ElementPlusConfig);
        app.use(RouterConfig);
        app.use(PiniaConfig);
        app.use(HttpConfig);
    }
}
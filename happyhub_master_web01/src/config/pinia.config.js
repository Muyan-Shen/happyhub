import {createPinia} from "pinia";
import PiniaPluginPersist from 'pinia-plugin-persist';

const pinia = createPinia();
pinia.use(PiniaPluginPersist);
export default pinia;
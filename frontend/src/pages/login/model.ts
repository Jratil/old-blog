import dva from 'dva'

import auth from '@/services/index'

const LoginModal = {
    namespace: 'login',
    state: null,
    effects: {
        *login({ payload, callback }, { call, put }) {
            let data = yield call(auth.authLogin, payload)
            if (data && callback) callback(data)
        },
        *forget({ payload, callback }, { call, put }) {
            let data = yield call(auth.authForget, payload)
            if (data && callback) callback(data)
        },
        *register({ payload, callback }, { call, put }) {
            let data = yield call(auth.authRegister, payload)
            if (data && callback) callback(data)
        },
        *getVerifyCode({ payload, callback }, { call, put }) {
            let data = yield call(auth.authVerifyCode, payload)
            if (data && callback) callback(data)
        }
    },
    reducers: {
        saveQueryData: (state, { payload }) => {
            return {
                ...state,
                ...payload
            }
        }
    }
}

export default LoginModal

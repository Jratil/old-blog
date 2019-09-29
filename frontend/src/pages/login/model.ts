import dva from 'dva'

import auth from '@/services/index'

const LoginModal = {
    namespace: 'login',
    state: null,
    effects: {
        *login({ payload, callback }, { call, put }) {
            let data = yield call(auth.authorLogin, payload)
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

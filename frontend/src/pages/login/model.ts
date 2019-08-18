import dva from 'dva'

import auth from '@/services/index'

const LoginModal = {
    namespace: 'login',
    state: null,
    effects: {
        *login({ payload, callback }, { call, put }) {
            console.log(payload)
            let data = yield call(auth.authorLogin, payload)
            console.log(data, 23333)
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

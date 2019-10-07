import dva from 'dva'
import auth from '@/services/index'

const LoginModal = {
    namespace: 'overview',
    state: {
        articles: []
    },
    effects: {
        *getArticle({ payload, callback }, { call, put }) {
            let data = yield call(auth.overviewArticle, payload)
            const articles = data.data
            yield put({ type: 'saveQueryData', payload: { articles: articles } })
        },
        *likeArticle({ payload, callback }, { call, put }) {
            const { id, hasLike } = payload
            let data = yield call(hasLike ? auth.overviewArticleLikeReduce : auth.overviewArticleLikeAdd, { id })
            if (data.code === 0) yield put({ type: 'saveLikeData', payload })
        }
    },
    reducers: {
        saveQueryData: (state, { payload }) => {
            return {
                ...state,
                ...payload
            }
        },
        saveLikeData: (state, { payload }) => {
            const { articles } = state
            const { id, hasLike } = payload
            const num = hasLike ? -1 : 1
            return {
                ...state,
                articles: articles.map(item => {
                    if (item.id !== id) return item
                    return {
                        ...item,
                        like: item.like + num,
                        hasLike: !hasLike
                    }
                })
            }
        }
    }
}

export default LoginModal

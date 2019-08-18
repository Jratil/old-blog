export interface IRoute {
    path: string
    title?: string
    active?: string
    component?: string
    exclude?: number[]
    noShowInMenu?: boolean
    routes?: IRoute[]
    redirect?: string
}

export const menuRoutesData: IRoute[] = [
    {
        path: '/user',
        title: '用户主页',
        active: 'dashboard'
        // component: './dashboard',
        // exclude: [2]
    },
    {
        path: '/expire',
        title: '过期'
        // component: './expire',
        // noShowInMenu: true
    },
    {
        path: '/post',
        title: '文章详情'
        // active: 'projects'
    }
]

export const loginRoutes: IRoute[] = [
    {
        path: '/login',
        component: './login/index'
    },
    {
        path: '/register'
        // component: './login/Register'
    }
]

export default [
    {
        path: '/',
        component: '../layouts',
        routes: [
            {
                path: '/',
                redirect: './index'
            },
            ...menuRoutesData,
            ...loginRoutes
        ]
    }
]

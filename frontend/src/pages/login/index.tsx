import React from 'react'
import { router } from 'umi'
import { Dispatch } from 'redux'
import { Form, Input, Button } from 'antd'
import { FormComponentProps } from 'antd/lib/form'
import { MIcon } from '@/components'

import styles from './index.less'

interface MProps extends FormComponentProps {
    dispatch: Dispatch
}

const FormItem = Form.Item

const rules = { account: [{ required: true, message: '请输入账号' }], password: [{ required: true, message: '请输入密码' }] }

const Login: React.FC<MProps> = props => {
    const { dispatch, form } = props
    const { getFieldDecorator } = form

    const handleSubmit = evt => {
        evt.preventDefault()
        form.validateFields((err, payload) => {
            if (!err) dispatch({ type: 'login/login', payload })
        })
    }

    const handelRegister = evt => {
        evt.preventDefault()
        router.push('/register')
    }

    return (
        <Form onSubmit={handleSubmit}>
            <FormItem>
                {getFieldDecorator('account', {
                    rules: rules.account,
                    validateTrigger: 'onBlur'
                })(<Input prefix={<MIcon type="user" />} placeholder="Account"></Input>)}
            </FormItem>
            <FormItem>
                {getFieldDecorator('password', {
                    rules: rules.password,
                    validateTrigger: 'onBlur'
                })(<Input type="password" prefix={<MIcon type="lock" />} placeholder="Password"></Input>)}
            </FormItem>
            <FormItem>
                <div className={styles.btnWrapper}>
                    <Button type="primary" onClick={handleSubmit}>
                        登录
                    </Button>
                    <Button onClick={handelRegister}>注册</Button>
                </div>
            </FormItem>
        </Form>
    )
}

export default Form.create()(Login)

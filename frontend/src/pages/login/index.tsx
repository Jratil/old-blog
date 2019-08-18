import * as React from 'react'
import { router } from 'umi'
import { Dispatch } from 'redux'
import { connect } from 'dva'
import { Form, Input, Icon, Button } from 'antd'
import { FormComponentProps } from 'antd/lib/form'

import styles from './index.less'

interface XProps extends FormComponentProps {
    dispatch: Dispatch
}

const FormItem = Form.Item

const rules = { account: [{ required: true, message: '请输入账号' }], password: [{ required: true, message: '请输入密码' }] }

@connect()
class Login extends React.Component<XProps> {
    handleSubmit = evt => {
        evt.preventDefault()
        this.props.form.validateFields((err, payload) => {
            if (!err) this.props.dispatch({ type: 'login/login', payload })
        })
    }
    handelRegister = evt => {
        evt.preventDefault()
        router.push('/register')
    }
    render() {
        const { getFieldDecorator } = this.props.form

        return (
            <Form onSubmit={this.handleSubmit}>
                <FormItem>
                    {getFieldDecorator('account', {
                        rules: rules.account,
                        validateTrigger: 'onBlur'
                    })(<Input prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="Account"></Input>)}
                </FormItem>
                <FormItem>
                    {getFieldDecorator('password', {
                        rules: rules.password,
                        validateTrigger: 'onBlur'
                    })(<Input type="password" prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="Password"></Input>)}
                </FormItem>
                <FormItem>
                    <div className={styles.btnWrapper}>
                        <Button type="primary" onClick={this.handleSubmit}>
                            登录
                        </Button>
                        <Button onClick={this.handelRegister}>注册</Button>
                    </div>
                </FormItem>
            </Form>
        )
    }
}

export default Form.create()(Login)
